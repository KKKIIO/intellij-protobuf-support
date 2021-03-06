/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kkkiio.intellij.protobuf.proto.format

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.tree.TokenSet
import idea.plugin.protoeditor.ide.formatter.PbTextBlock
import idea.plugin.protoeditor.lang.psi.*


class PbBlockAlign {
    val fieldAlign: Alignment = Alignment.createAlignment(true)
    val assignAlign: Alignment = Alignment.createAlignment(true)
}

private fun ASTNode.isEmpty(): Boolean {
    return elementType === TokenType.WHITE_SPACE || textLength == 0
}

private fun ASTNode.deepestLeaf(): ASTNode {
    var n = this
    while (n.firstChildNode != null
            && n.textRange == n.firstChildNode.textRange) {
        n = n.firstChildNode
    }
    return n
}

private val leafSet = TokenSet.create(PbTypes.SYMBOL_PATH, PbTypes.TYPE_NAME)

class PbBlock internal constructor(node: ASTNode,
                                   wrap: Wrap?,
                                   alignment: Alignment?,
                                   private val spacingBuilder: SpacingBuilder,
                                   blockAlign: PbBlockAlign?,
                                   private val settings: CommonCodeStyleSettings) : AbstractBlock(node, wrap, alignment) {
    private val blockAlign: PbBlockAlign? = if (node.psi is PbBlockBody) PbBlockAlign() else blockAlign

    override fun isLeaf(): Boolean = myNode.firstChildNode == null || leafSet.contains(myNode.elementType)

    override fun buildChildren(): List<Block> {
        if (isLeaf) {
            return emptyList()
        }
        return node.getChildren(null)
                .filterNot(ASTNode::isEmpty)
                .map { childNode ->
                    if (childNode.elementType is PbTextElementType) {
                        return@map PbTextBlock(childNode, myWrap, myAlignment, spacingBuilder)
                    }
                    val childBlockNode = if (leafSet.contains(childNode.elementType)) childNode else childNode.deepestLeaf()
                    val alignment = when {
                        settings.ALIGN_GROUP_FIELD_DECLARATIONS && blockAlign != null -> when {
                            // todo: better identify field name
                            node.psi is PbField && childBlockNode.elementType === ProtoTokenTypes.IDENTIFIER_LITERAL ->
                                blockAlign.fieldAlign
                            childBlockNode.elementType === ProtoTokenTypes.ASSIGN -> blockAlign.assignAlign
                            else -> myAlignment
                        }
                        else -> myAlignment
                    }

                    return@map PbBlock(childBlockNode,
                            myWrap,
                            alignment,
                            spacingBuilder,
                            blockAlign,
                            settings)
                }
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return if (node.psi is PbField && (child2 as? ASTBlock)?.node?.elementType === ProtoTokenTypes.IDENTIFIER_LITERAL) {
            Spacing.createSpacing(1, 1, 0, settings.KEEP_LINE_BREAKS, settings.KEEP_BLANK_LINES_IN_CODE)
        } else {
            spacingBuilder.getSpacing(this, child1, child2)
        }
    }

    override fun getIndent(): Indent? {
        if (node.isEmpty()) {
            return null
        }
        val psi = node.psi
        val parent = psi.parent
        // Block children except for the start and end tokens are indented.
        // For everything remaining, no indent.
        return when {
            parent is ProtoBlockBody -> {
                // The start and end tokens are not indented.
                // Everything else in a block body is indented.
                if (psi == parent.start || psi == parent.end) {
                    Indent.getNoneIndent()
                } else {
                    Indent.getNormalIndent()
                }
            }
            psi is ProtoBlockBody -> {
                Indent.getNoneIndent()
            }
            ProtoTokenTypes.SEMI == node.elementType -> {
                Indent.getNoneIndent()
            }
            ProtoTokenTypes.BLOCK_COMMENT == node.elementType || ProtoTokenTypes.LINE_COMMENT == node.elementType -> {
                Indent.getNoneIndent()
            }
            isLeaf || parent is PbStatement -> {
                Indent.getContinuationWithoutFirstIndent()
            }
            else -> {
                // For everything remaining, no indent.
                Indent.getNoneIndent()
            }
        }
    }

    public override fun getChildIndent(): Indent? = when (node.psi) {
        is PbStatement -> Indent.getContinuationWithoutFirstIndent()
        is ProtoBlockBody -> Indent.getNormalIndent()
        else -> Indent.getNoneIndent()
    }
}

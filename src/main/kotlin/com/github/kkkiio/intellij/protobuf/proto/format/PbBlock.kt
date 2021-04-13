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
import idea.plugin.protoeditor.ide.formatter.PbTextBlock
import idea.plugin.protoeditor.lang.psi.*


class PbBlockAlign {
    val fieldAlign: Alignment = Alignment.createAlignment(true)
    val assignAlign: Alignment = Alignment.createAlignment(true)
}

/** A formatting block for protobuf elements.  */
class PbBlock internal constructor(node: ASTNode,
                                   wrap: Wrap?,
                                   alignment: Alignment?,
                                   private val spacingBuilder: SpacingBuilder,
                                   blockAlign: PbBlockAlign?,
                                   private val protoSettings: CommonCodeStyleSettings) : AbstractBlock(node, wrap, alignment) {
    private val blockAlign: PbBlockAlign? = if (node.psi is PbBlockBody) PbBlockAlign() else blockAlign

    override fun buildChildren(): List<Block> {
        if (isLeaf) {
            return emptyList()
        }
        return myNode.getChildren(null).filterNot { isEmpty(it) }.map {
            if (it.elementType is PbTextElementType) {
                PbTextBlock(it, myWrap, myAlignment, spacingBuilder)
            } else {
                val deepestLeafChild = deepestLeaf(it)
                val alignment = when {
                    protoSettings.ALIGN_GROUP_FIELD_DECLARATIONS && blockAlign != null && it is ProtoLeafElement -> when {
                        deepestLeafChild.elementType === ProtoTokenTypes.IDENTIFIER_LITERAL ->
                            blockAlign.fieldAlign
                        deepestLeafChild.elementType === ProtoTokenTypes.ASSIGN -> blockAlign.assignAlign
                        else -> myAlignment
                    }
                    else -> myAlignment
                }
                PbBlock(deepestLeafChild,
                        myWrap,
                        alignment,
                        spacingBuilder,
                        blockAlign,
                        protoSettings)
            }
        }
    }

    override fun getIndent(): Indent? {
        if (isEmpty(myNode)) {
            return null
        }
        val psi = myNode.psi
        val parent = psi.parent
        // Block children except for the start and end tokens are indented.
        if (parent is ProtoBlockBody) {
            return if (psi == parent.start || psi == parent.end) {
                // The start and end tokens are not indented.
                Indent.getNoneIndent()
            } else {
                // Everything else in a block body is indented.
                Indent.getNormalIndent()
            }
        }
        // Blocks handle their own indenting.
        if (psi is ProtoBlockBody) {
            return Indent.getNoneIndent()
        }
        // Semicolons are not indented.
        if (ProtoTokenTypes.SEMI == myNode.elementType) {
            return Indent.getNoneIndent()
        }
        // Comments outside of a block are not indented.
        if (ProtoTokenTypes.BLOCK_COMMENT == myNode.elementType || ProtoTokenTypes.LINE_COMMENT == myNode.elementType) {
            return Indent.getNoneIndent()
        }
        // Leaves and statement children get continuation indents.
        return if (isLeaf || parent is PbStatement) {
            Indent.getContinuationWithoutFirstIndent()
        } else {
            // For everything remaining, no indent.
            Indent.getNoneIndent()
        }
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean {
        return myNode.firstChildNode == null
    }

    public override fun getChildIndent(): Indent? = when (myNode.psi) {
        is PbStatement -> {
            Indent.getContinuationWithoutFirstIndent()
        }
        is ProtoBlockBody -> {
            Indent.getNormalIndent()
        }
        else -> {
            Indent.getNoneIndent()
        }
    }

    private fun isEmpty(node: ASTNode): Boolean {
        return node.elementType === TokenType.WHITE_SPACE || node.textLength == 0
    }

    /**
     * Find the deepest node with the same text range
     */
    private fun deepestLeaf(node: ASTNode): ASTNode {
        var n = node
        while (n.firstChildNode != null
                && n.textRange == n.firstChildNode.textRange) {
            n = n.firstChildNode
        }
        return n
    }

}
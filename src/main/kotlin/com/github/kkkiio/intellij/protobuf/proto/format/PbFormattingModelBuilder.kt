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
import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.formatter.FormattingDocumentModelImpl
import com.intellij.psi.formatter.PsiBasedFormattingModel
import com.intellij.psi.tree.TokenSet
import idea.plugin.protoeditor.lang.PbLanguage
import idea.plugin.protoeditor.lang.psi.ProtoTokenTypes

/**
 * A [FormattingModelBuilder] for proto files.
 */
class PbFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(element: PsiElement,
                             settings: CodeStyleSettings): FormattingModel {
        val file = element.containingFile
        val protoSettings = settings.getCommonSettings(PbLanguage.INSTANCE)
        return PsiBasedFormattingModel(file,
                PbBlock(element.node,
                        Wrap.createWrap(WrapType.NONE, false),
                        null,
                        createSpaceBuilder(settings, protoSettings),
                        null,
                        protoSettings),
                FormattingDocumentModelImpl.createOn(file))
    }
}

private fun createSpaceBuilder(settings: CodeStyleSettings,
                               protoSettings: CommonCodeStyleSettings): SpacingBuilder =
        SpacingBuilder(settings, PbLanguage.INSTANCE).withinPair(ProtoTokenTypes.LBRACE,
                ProtoTokenTypes.RBRACE)
                .spaceIf(protoSettings.SPACE_WITHIN_BRACES,
                        false)
                .withinPair(ProtoTokenTypes.LBRACK,
                        ProtoTokenTypes.RBRACK)
                .spaceIf(protoSettings.SPACE_WITHIN_BRACKETS,
                        false)
                .withinPair(ProtoTokenTypes.LPAREN,
                        ProtoTokenTypes.RPAREN)
                .spaceIf(protoSettings.SPACE_WITHIN_PARENTHESES,
                        false)
                .before(ProtoTokenTypes.COMMA)
                .spaceIf(protoSettings.SPACE_BEFORE_COMMA)
                .after(ProtoTokenTypes.COMMA)
                .spaceIf(protoSettings.SPACE_AFTER_COMMA)
                .around(ProtoTokenTypes.ASSIGN)
                .spaceIf(protoSettings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
                .after(TokenSet.create(ProtoTokenTypes.OPTIONAL, ProtoTokenTypes.REQUIRED, ProtoTokenTypes.REPEATED))
                .spaces(1)
                .before(ProtoTokenTypes.SEMI)
                .spaces(0)
                .withinPair(ProtoTokenTypes.LT, ProtoTokenTypes.GT)
                .spaceIf(protoSettings.SPACE_WITHIN_BRACES, false)
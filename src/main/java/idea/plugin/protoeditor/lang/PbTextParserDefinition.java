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
package idea.plugin.protoeditor.lang;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import idea.plugin.protoeditor.lang.lexer.ProtoLexer;
import idea.plugin.protoeditor.lang.parser.PbTextParser;
import idea.plugin.protoeditor.lang.psi.PbTextTypes;
import idea.plugin.protoeditor.lang.psi.ProtoTokenTypes;
import idea.plugin.protoeditor.lang.psi.impl.PbTextFileImpl;
import org.jetbrains.annotations.NotNull;

/** A {@link ParserDefinition} for prototext files. */
public class PbTextParserDefinition implements ParserDefinition {
  public static final PbTextParserDefinition INSTANCE = new PbTextParserDefinition();

  public static final TokenSet WHITE_SPACE = TokenSet.create(TokenType.WHITE_SPACE);
  public static final TokenSet COMMENTS =
      TokenSet.create(ProtoTokenTypes.LINE_COMMENT, ProtoTokenTypes.BLOCK_COMMENT);
  public static final TokenSet STRINGS = TokenSet.create(ProtoTokenTypes.STRING_LITERAL);

  public static final IFileElementType FILE = new IFileElementType(PbTextLanguage.INSTANCE);

  public PbTextParserDefinition() {}

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return ProtoLexer.forPrototext();
  }

  @Override
  public PsiParser createParser(final Project project) {
    return new PbTextParser();
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return WHITE_SPACE;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return COMMENTS;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return STRINGS;
  }

  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  @Override
  public PsiFile createFile(FileViewProvider viewProvider) {
    return new PbTextFileImpl(viewProvider, PbTextLanguage.INSTANCE);
  }

  @Override
  public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
    return SpaceRequirements.MAY;
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    return PbTextTypes.Factory.createElement(node);
  }
}

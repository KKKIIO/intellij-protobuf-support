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
package idea.plugin.protoeditor.ide.highlighter;

import com.intellij.lexer.LayeredLexer;
import idea.plugin.protoeditor.lang.lexer.ProtoLexer;
import idea.plugin.protoeditor.lang.lexer.StringLexer;
import idea.plugin.protoeditor.lang.psi.ProtoTokenTypes;

/**
 * This lexer returns all non-keyword tokens, and should be used in conjunction with {@link
 * PbHighlightingAnnotator}. For keywords, the lexer returns the type IDENTIFIER_LITERAL.
 * Information unavailable to the lexer is required to properly determine whether keyword tokens
 * should be highlighted.
 *
 * <p>Additionally, this lexer layers on string literal subtokens for highlighting valid and invalid
 * escape sequences.
 */
public class PbPartialHighlightingLexer extends LayeredLexer {

  PbPartialHighlightingLexer() {
    super(ProtoLexer.forProtobufWithoutKeywords());
    registerLayer(
        StringLexer.mergingStringLexer(ProtoTokenTypes.STRING_LITERAL),
        ProtoTokenTypes.STRING_LITERAL);
  }
}

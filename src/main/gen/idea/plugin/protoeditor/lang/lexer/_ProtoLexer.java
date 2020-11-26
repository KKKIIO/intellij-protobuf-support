/* The following code was generated by JFlex 1.7.0 tweaked for IntelliJ platform */

// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package idea.plugin.protoeditor.lang.lexer;

import idea.plugin.protoeditor.lang.lexer.ProtoLexer.CommentStyle;
import idea.plugin.protoeditor.lang.psi.ProtoTokenTypes;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

@SuppressWarnings("fallthrough")

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>proto.flex</tt>
 */
public class _ProtoLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int COMMENT = 2;
  public static final int AFTER_NUMBER = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2, 2
  };

  /** 
   * Translates characters to character classes
   * Chosen bits are [8, 6, 7]
   * Total runtime size is 1040 bytes
   */
  public static int ZZ_CMAP(int ch) {
    return ZZ_CMAP_A[ZZ_CMAP_Y[ZZ_CMAP_Z[ch>>13]|((ch>>7)&0x3f)]|(ch&0x7f)];
  }

  /* The ZZ_CMAP_Z table has 136 entries */
  static final char ZZ_CMAP_Z[] = zzUnpackCMap(
    "\1\0\207\100");

  /* The ZZ_CMAP_Y table has 128 entries */
  static final char ZZ_CMAP_Y[] = zzUnpackCMap(
    "\1\0\177\200");

  /* The ZZ_CMAP_A table has 256 entries */
  static final char ZZ_CMAP_A[] = zzUnpackCMap(
    "\11\0\1\7\1\10\3\7\22\0\1\7\1\6\1\23\1\13\3\6\1\21\1\32\1\37\1\12\1\17\1\26"+
    "\1\34\1\15\1\11\1\2\7\5\2\3\1\25\1\40\1\33\1\24\1\27\2\6\4\4\1\16\1\20\21"+
    "\1\1\14\2\1\1\31\1\22\1\36\1\6\1\62\1\6\1\44\1\65\1\63\1\41\1\42\1\43\1\56"+
    "\1\1\1\54\1\61\1\64\1\46\1\51\1\50\1\55\1\60\1\66\1\57\1\53\1\47\1\45\1\67"+
    "\1\71\1\52\1\70\1\1\1\30\1\6\1\35\1\6\201\0");

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\1\2\2\3\1\4\1\5\1\6\1\7"+
    "\1\10\2\11\1\12\1\13\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\14\2"+
    "\1\0\1\27\1\30\1\31\1\3\1\0\1\32\1\0"+
    "\1\33\1\34\3\11\3\2\1\35\20\2\1\36\1\3"+
    "\1\37\1\32\1\0\5\2\1\40\1\41\13\2\1\42"+
    "\4\2\1\36\1\2\1\43\1\2\1\44\17\2\1\45"+
    "\1\36\7\2\1\46\1\2\1\47\10\2\1\50\3\2"+
    "\1\51\1\52\1\53\1\54\5\2\1\55\1\2\1\56"+
    "\1\2\1\57\1\60\1\2\1\61\3\2\1\62\2\2"+
    "\1\63\1\64\1\65\1\66\2\2\1\67\1\70";

  private static int [] zzUnpackAction() {
    int [] result = new int[175];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\72\0\164\0\256\0\350\0\u0122\0\u015c\0\256"+
    "\0\u0196\0\u01d0\0\256\0\u020a\0\u0244\0\u027e\0\256\0\256"+
    "\0\256\0\256\0\256\0\256\0\256\0\256\0\256\0\256"+
    "\0\256\0\256\0\256\0\u02b8\0\u02f2\0\u032c\0\u0366\0\u03a0"+
    "\0\u03da\0\u0414\0\u044e\0\u0488\0\u04c2\0\u04fc\0\u0536\0\u0570"+
    "\0\u05aa\0\256\0\u05e4\0\u061e\0\u0658\0\u0692\0\u06cc\0\256"+
    "\0\256\0\256\0\u0706\0\u0740\0\u077a\0\u07b4\0\u07ee\0\350"+
    "\0\u0828\0\u0862\0\u089c\0\u08d6\0\u0910\0\u094a\0\u0984\0\u09be"+
    "\0\u09f8\0\u0a32\0\u0a6c\0\u0aa6\0\u0ae0\0\u0b1a\0\u0b54\0\u0b8e"+
    "\0\u0bc8\0\u0658\0\256\0\u0c02\0\u0c3c\0\u0c76\0\u0cb0\0\u0cea"+
    "\0\u0d24\0\u0d5e\0\350\0\350\0\u0d98\0\u0dd2\0\u0e0c\0\u0e46"+
    "\0\u0e80\0\u0eba\0\u0ef4\0\u0f2e\0\u0f68\0\u0fa2\0\u0fdc\0\350"+
    "\0\u1016\0\u1050\0\u108a\0\u10c4\0\u10fe\0\u1138\0\350\0\u1172"+
    "\0\350\0\u11ac\0\u11e6\0\u1220\0\u125a\0\u1294\0\u12ce\0\u1308"+
    "\0\u1342\0\u137c\0\u13b6\0\u13f0\0\u142a\0\u1464\0\u149e\0\u14d8"+
    "\0\350\0\256\0\u1512\0\u154c\0\u1586\0\u15c0\0\u15fa\0\u1634"+
    "\0\u166e\0\350\0\u16a8\0\350\0\u16e2\0\u171c\0\u1756\0\u1790"+
    "\0\u17ca\0\u1804\0\u183e\0\u1878\0\350\0\u18b2\0\u18ec\0\u1926"+
    "\0\350\0\350\0\350\0\u1960\0\u199a\0\u19d4\0\u1a0e\0\u1a48"+
    "\0\u1a82\0\350\0\u1abc\0\350\0\u1af6\0\350\0\350\0\u1b30"+
    "\0\350\0\u1b6a\0\u1ba4\0\u1bde\0\350\0\u1c18\0\u1c52\0\350"+
    "\0\350\0\350\0\350\0\u1c8c\0\u1cc6\0\350\0\350";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[175];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\1\5\1\6\1\7\1\5\1\7\1\10\2\11"+
    "\1\12\1\10\1\13\1\5\1\14\1\5\1\10\1\5"+
    "\1\15\1\10\1\16\1\17\1\20\1\21\1\22\1\23"+
    "\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33"+
    "\1\34\1\35\4\5\1\36\1\5\1\37\1\5\1\40"+
    "\1\41\1\42\1\43\1\44\1\45\1\46\7\5\1\47"+
    "\11\0\1\50\1\0\1\51\56\0\1\52\1\53\2\52"+
    "\1\53\7\52\1\53\1\52\1\53\1\52\1\53\20\52"+
    "\31\53\73\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\31\5\2\0\1\54\2\0\1\54\6\0"+
    "\1\55\1\56\1\57\1\0\1\60\21\0\1\57\1\60"+
    "\6\0\1\55\21\0\2\7\1\0\1\7\7\0\1\56"+
    "\1\57\1\0\1\60\21\0\1\57\1\60\35\0\2\11"+
    "\72\0\2\61\61\0\2\56\1\0\1\56\64\0\10\15"+
    "\1\0\10\15\1\62\1\63\47\15\10\16\1\0\11\16"+
    "\1\64\1\62\46\16\1\0\5\5\6\0\1\5\1\0"+
    "\1\5\1\0\1\5\20\0\1\5\1\65\27\5\1\0"+
    "\5\5\6\0\1\5\1\0\1\5\1\0\1\5\20\0"+
    "\7\5\1\66\1\5\1\67\17\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\14\5\1\70"+
    "\1\5\1\71\12\5\1\0\5\5\6\0\1\5\1\0"+
    "\1\5\1\0\1\5\20\0\1\5\1\72\1\5\1\73"+
    "\25\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\1\5\1\74\4\5\1\75\20\5\1\76"+
    "\1\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\10\5\1\77\20\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\7\5\1\100"+
    "\7\5\1\101\11\5\1\0\5\5\6\0\1\5\1\0"+
    "\1\5\1\0\1\5\20\0\16\5\1\102\12\5\1\0"+
    "\5\5\6\0\1\5\1\0\1\5\1\0\1\5\20\0"+
    "\1\5\1\103\15\5\1\104\11\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\3\5\1\105"+
    "\1\106\24\5\1\0\5\5\6\0\1\5\1\0\1\5"+
    "\1\0\1\5\20\0\12\5\1\107\16\5\1\0\5\5"+
    "\6\0\1\5\1\0\1\5\1\0\1\5\20\0\1\5"+
    "\1\110\27\5\11\0\1\51\1\111\57\0\10\51\1\0"+
    "\61\51\1\0\5\53\6\0\1\53\1\0\1\53\1\0"+
    "\1\53\20\0\31\53\2\0\1\54\2\0\1\54\66\0"+
    "\4\112\10\0\1\112\1\0\1\112\20\0\4\112\16\0"+
    "\1\112\1\0\1\112\6\0\2\56\1\0\1\56\10\0"+
    "\1\57\1\0\1\113\21\0\1\57\1\113\30\0\2\114"+
    "\1\0\1\114\11\0\1\115\14\0\1\115\35\0\10\15"+
    "\1\0\61\15\10\16\1\0\61\16\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\2\5\1\116"+
    "\26\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\4\5\1\117\24\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\6\5\1\120"+
    "\22\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\4\5\1\121\24\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\12\5\1\122"+
    "\16\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\11\5\1\123\5\5\1\124\11\5\1\0"+
    "\5\5\6\0\1\5\1\0\1\5\1\0\1\5\20\0"+
    "\16\5\1\125\12\5\1\0\5\5\6\0\1\5\1\0"+
    "\1\5\1\0\1\5\20\0\16\5\1\126\12\5\1\0"+
    "\5\5\6\0\1\5\1\0\1\5\1\0\1\5\20\0"+
    "\7\5\1\127\21\5\1\0\5\5\6\0\1\5\1\0"+
    "\1\5\1\0\1\5\20\0\17\5\1\130\11\5\1\0"+
    "\5\5\6\0\1\5\1\0\1\5\1\0\1\5\20\0"+
    "\1\5\1\131\27\5\1\0\5\5\6\0\1\5\1\0"+
    "\1\5\1\0\1\5\20\0\6\5\1\132\22\5\1\0"+
    "\5\5\6\0\1\5\1\0\1\5\1\0\1\5\20\0"+
    "\14\5\1\133\14\5\1\0\5\5\6\0\1\5\1\0"+
    "\1\5\1\0\1\5\20\0\6\5\1\134\3\5\1\135"+
    "\4\5\1\136\5\5\1\137\3\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\22\5\1\140"+
    "\6\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\22\5\1\141\6\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\24\5\1\142"+
    "\4\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\14\5\1\143\14\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\3\5\1\144"+
    "\25\5\12\111\1\145\57\111\2\0\2\114\1\0\1\114"+
    "\12\0\1\113\22\0\1\113\30\0\2\114\1\0\1\114"+
    "\65\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\3\5\1\146\25\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\10\5\1\147\20\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\1\5\1\150\27\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\1\5\1\151\27\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\12\5\1\152\16\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\26\5\1\153\2\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\1\5\1\154\27\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\6\5\1\155\22\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\14\5\1\156\14\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\14\5\1\157\14\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\13\5\1\160\15\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\4\5\1\161\24\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\4\5\1\162\24\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\1\5\1\163\27\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\1\5\1\164\27\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\4\5\1\165\24\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\23\5\1\166\5\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\5\5\1\167\23\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\7\5\1\170\21\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\23\5\1\171\5\5"+
    "\11\111\1\172\1\145\57\111\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\4\5\1\173\24\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\7\5\1\174\21\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\3\5\1\175\25\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\13\5\1\176\15\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\3\5\1\177\25\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\3\5\1\200\25\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\16\5\1\201\12\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\2\5\1\202\26\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\14\5\1\203\14\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\17\5\1\204\11\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\16\5\1\205\12\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\16\5\1\206\12\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\3\5\1\207\25\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\13\5\1\210\15\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\3\5\1\211\25\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\13\5\1\212\15\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\21\5\1\213\7\5"+
    "\1\0\5\5\6\0\1\5\1\0\1\5\1\0\1\5"+
    "\20\0\5\5\1\214\23\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\1\215\11\5\1\216"+
    "\16\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\15\5\1\217\13\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\22\5\1\220"+
    "\6\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\10\5\1\221\20\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\11\5\1\222"+
    "\17\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\6\5\1\223\22\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\7\5\1\224"+
    "\21\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\7\5\1\225\21\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\26\5\1\226"+
    "\2\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\6\5\1\227\22\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\16\5\1\230"+
    "\12\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\15\5\1\231\13\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\22\5\1\232"+
    "\6\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\7\5\1\233\21\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\6\5\1\234"+
    "\22\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\13\5\1\235\15\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\1\5\1\236"+
    "\27\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\1\5\1\237\27\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\3\5\1\240"+
    "\25\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\12\5\1\241\16\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\1\5\1\242"+
    "\27\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\1\5\1\243\27\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\1\5\1\244"+
    "\27\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\1\5\1\245\27\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\3\5\1\246"+
    "\25\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\14\5\1\247\14\5\1\0\5\5\6\0"+
    "\1\5\1\0\1\5\1\0\1\5\20\0\5\5\1\250"+
    "\23\5\1\0\5\5\6\0\1\5\1\0\1\5\1\0"+
    "\1\5\20\0\1\251\30\5\1\0\5\5\6\0\1\5"+
    "\1\0\1\5\1\0\1\5\20\0\1\252\30\5\1\0"+
    "\5\5\6\0\1\5\1\0\1\5\1\0\1\5\20\0"+
    "\1\253\30\5\1\0\5\5\6\0\1\5\1\0\1\5"+
    "\1\0\1\5\20\0\10\5\1\254\20\5\1\0\5\5"+
    "\6\0\1\5\1\0\1\5\1\0\1\5\20\0\7\5"+
    "\1\255\21\5\1\0\5\5\6\0\1\5\1\0\1\5"+
    "\1\0\1\5\20\0\1\5\1\256\27\5\1\0\5\5"+
    "\6\0\1\5\1\0\1\5\1\0\1\5\20\0\12\5"+
    "\1\257\16\5";

  private static int [] zzUnpackTrans() {
    int [] result = new int[7424];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\11\3\1\1\11\2\1\1\11\3\1\15\11"+
    "\14\1\1\0\1\1\1\11\2\1\1\0\1\1\1\0"+
    "\3\11\30\1\1\11\1\1\1\0\54\1\1\11\65\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[175];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  private CommentStyle commentStyle;
  private boolean allowFloatCast;
  private boolean returnKeywords;

  public _ProtoLexer(CommentStyle commentStyle, boolean allowFloatCast, boolean returnKeywords) {
    this(null);
    this.commentStyle = commentStyle;
    this.allowFloatCast = allowFloatCast;
    this.returnKeywords = returnKeywords;
  }

  private IElementType keyword(IElementType type) {
    return returnKeywords ? type : ProtoTokenTypes.IDENTIFIER_LITERAL;
  }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public _ProtoLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    int size = 0;
    for (int i = 0, length = packed.length(); i < length; i += 2) {
      size += packed.charAt(i);
    }
    char[] map = new char[size];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < packed.length()) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      {@code false}, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position {@code pos} from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + ZZ_CMAP(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { return com.intellij.psi.TokenType.BAD_CHARACTER;
            } 
            // fall through
          case 57: break;
          case 2: 
            { return ProtoTokenTypes.IDENTIFIER_LITERAL;
            } 
            // fall through
          case 58: break;
          case 3: 
            { yybegin(AFTER_NUMBER); return ProtoTokenTypes.INTEGER_LITERAL;
            } 
            // fall through
          case 59: break;
          case 4: 
            { return ProtoTokenTypes.SYMBOL;
            } 
            // fall through
          case 60: break;
          case 5: 
            { return com.intellij.psi.TokenType.WHITE_SPACE;
            } 
            // fall through
          case 61: break;
          case 6: 
            { return ProtoTokenTypes.SLASH;
            } 
            // fall through
          case 62: break;
          case 7: 
            { if (commentStyle == CommentStyle.SH_STYLE) {
      // Push back the symbol and enter COMMENT state to match the comment.
      yypushback(1);
      yybegin(COMMENT);
    } else {
      // Return SYMBOL for the '#'.
      return ProtoTokenTypes.SYMBOL;
    }
            } 
            // fall through
          case 63: break;
          case 8: 
            { return ProtoTokenTypes.DOT;
            } 
            // fall through
          case 64: break;
          case 9: 
            { return ProtoTokenTypes.STRING_LITERAL;
            } 
            // fall through
          case 65: break;
          case 10: 
            { return ProtoTokenTypes.ASSIGN;
            } 
            // fall through
          case 66: break;
          case 11: 
            { return ProtoTokenTypes.COLON;
            } 
            // fall through
          case 67: break;
          case 12: 
            { return ProtoTokenTypes.COMMA;
            } 
            // fall through
          case 68: break;
          case 13: 
            { return ProtoTokenTypes.GT;
            } 
            // fall through
          case 69: break;
          case 14: 
            { return ProtoTokenTypes.LBRACE;
            } 
            // fall through
          case 70: break;
          case 15: 
            { return ProtoTokenTypes.LBRACK;
            } 
            // fall through
          case 71: break;
          case 16: 
            { return ProtoTokenTypes.LPAREN;
            } 
            // fall through
          case 72: break;
          case 17: 
            { return ProtoTokenTypes.LT;
            } 
            // fall through
          case 73: break;
          case 18: 
            { return ProtoTokenTypes.MINUS;
            } 
            // fall through
          case 74: break;
          case 19: 
            { return ProtoTokenTypes.RBRACE;
            } 
            // fall through
          case 75: break;
          case 20: 
            { return ProtoTokenTypes.RBRACK;
            } 
            // fall through
          case 76: break;
          case 21: 
            { return ProtoTokenTypes.RPAREN;
            } 
            // fall through
          case 77: break;
          case 22: 
            { return ProtoTokenTypes.SEMI;
            } 
            // fall through
          case 78: break;
          case 23: 
            { yybegin(YYINITIAL); return ProtoTokenTypes.LINE_COMMENT;
            } 
            // fall through
          case 79: break;
          case 24: 
            { yybegin(YYINITIAL); yypushback(yylength());
            } 
            // fall through
          case 80: break;
          case 25: 
            { yybegin(YYINITIAL); return ProtoTokenTypes.IDENTIFIER_AFTER_NUMBER;
            } 
            // fall through
          case 81: break;
          case 26: 
            { yybegin(AFTER_NUMBER); return ProtoTokenTypes.FLOAT_LITERAL;
            } 
            // fall through
          case 82: break;
          case 27: 
            { yybegin(AFTER_NUMBER);
    if (allowFloatCast) {
      return ProtoTokenTypes.FLOAT_LITERAL;
    } else {
      yypushback(1); // Push the 'f' back
      return ProtoTokenTypes.INTEGER_LITERAL;
    }
            } 
            // fall through
          case 83: break;
          case 28: 
            { if (commentStyle == CommentStyle.C_STYLE) {
      // Push back both characters and match with either CLineComment or CBlockComment in the
      // COMMENT state.
      yypushback(2);
      yybegin(COMMENT);
    } else {
      // Push back the trailing '/' or '*' and return SLASH for the leading '/'.
      yypushback(1);
      return ProtoTokenTypes.SLASH;
    }
            } 
            // fall through
          case 84: break;
          case 29: 
            { return keyword(ProtoTokenTypes.TO);
            } 
            // fall through
          case 85: break;
          case 30: 
            { yybegin(YYINITIAL); return ProtoTokenTypes.BLOCK_COMMENT;
            } 
            // fall through
          case 86: break;
          case 31: 
            { yybegin(AFTER_NUMBER);
    if (!allowFloatCast) {
      yypushback(1); // Push the 'f' back
    }
    return ProtoTokenTypes.FLOAT_LITERAL;
            } 
            // fall through
          case 87: break;
          case 32: 
            { return keyword(ProtoTokenTypes.MAX);
            } 
            // fall through
          case 88: break;
          case 33: 
            { return keyword(ProtoTokenTypes.MAP);
            } 
            // fall through
          case 89: break;
          case 34: 
            { return keyword(ProtoTokenTypes.RPC);
            } 
            // fall through
          case 90: break;
          case 35: 
            { return keyword(ProtoTokenTypes.ENUM);
            } 
            // fall through
          case 91: break;
          case 36: 
            { return keyword(ProtoTokenTypes.TRUE);
            } 
            // fall through
          case 92: break;
          case 37: 
            { return keyword(ProtoTokenTypes.WEAK);
            } 
            // fall through
          case 93: break;
          case 38: 
            { return keyword(ProtoTokenTypes.ONEOF);
            } 
            // fall through
          case 94: break;
          case 39: 
            { return keyword(ProtoTokenTypes.GROUP);
            } 
            // fall through
          case 95: break;
          case 40: 
            { return keyword(ProtoTokenTypes.EXTEND);
            } 
            // fall through
          case 96: break;
          case 41: 
            { return keyword(ProtoTokenTypes.STREAM);
            } 
            // fall through
          case 97: break;
          case 42: 
            { return keyword(ProtoTokenTypes.SYNTAX);
            } 
            // fall through
          case 98: break;
          case 43: 
            { return keyword(ProtoTokenTypes.IMPORT);
            } 
            // fall through
          case 99: break;
          case 44: 
            { return keyword(ProtoTokenTypes.OPTION);
            } 
            // fall through
          case 100: break;
          case 45: 
            { return keyword(ProtoTokenTypes.PUBLIC);
            } 
            // fall through
          case 101: break;
          case 46: 
            { return keyword(ProtoTokenTypes.DEFAULT);
            } 
            // fall through
          case 102: break;
          case 47: 
            { return keyword(ProtoTokenTypes.MESSAGE);
            } 
            // fall through
          case 103: break;
          case 48: 
            { return keyword(ProtoTokenTypes.SERVICE);
            } 
            // fall through
          case 104: break;
          case 49: 
            { return keyword(ProtoTokenTypes.RETURNS);
            } 
            // fall through
          case 105: break;
          case 50: 
            { return keyword(ProtoTokenTypes.PACKAGE);
            } 
            // fall through
          case 106: break;
          case 51: 
            { return keyword(ProtoTokenTypes.OPTIONAL);
            } 
            // fall through
          case 107: break;
          case 52: 
            { return keyword(ProtoTokenTypes.RESERVED);
            } 
            // fall through
          case 108: break;
          case 53: 
            { return keyword(ProtoTokenTypes.REPEATED);
            } 
            // fall through
          case 109: break;
          case 54: 
            { return keyword(ProtoTokenTypes.REQUIRED);
            } 
            // fall through
          case 110: break;
          case 55: 
            { return keyword(ProtoTokenTypes.JSON_NAME);
            } 
            // fall through
          case 111: break;
          case 56: 
            { return keyword(ProtoTokenTypes.EXTENSIONS);
            } 
            // fall through
          case 112: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}

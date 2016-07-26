/* The following code was generated by JFlex 1.4.3 on 6/14/10 12:55 PM */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * This file is part of Beaver Parser Generator.                       *
 * Copyright (C) 2003,2005 Alexander Demenchuk <alder@softanvil.com>.  *
 * All rights reserved.                                                *
 * See the file "LICENSE" for the terms and conditions for copying,    *
 * distribution and modification of Beaver.                            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package beaver.spec.parser;

import beaver.Symbol;
import beaver.Scanner;
import beaver.spec.parser.GrammarParser.Terminals;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 6/14/10 12:55 PM from the specification file
 * <tt>/home/alex/Projects/beaver_0.9/etc/GrammarScanner.flex</tt>
 */
public class GrammarScanner extends Scanner {

  /** This character denotes the end of file */
    public final int YYEOF = -1;

  /** initial size of the lookahead buffer */
    private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
    public static final int CODE = 2;
    public static final int EOL_COMMENT = 8;
    public static final int EOF_MLN_COMMENT = 14;
    public static final int EOF_CODE = 12;
    public static final int CODE_END = 10;
    public static final int MLN_COMMENT = 6;
    public static final int YYINITIAL = 0;
    public static final int EOF_EOL_COMMENT = 16;
    public static final int TEXT = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
    private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7,
     8, 8 };

  /** 
   * Translates characters to character classes
   */
    private static final String ZZ_CMAP_PACKED =
     "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\3\1\0\1\6" +
     "\2\0\1\7\4\0\1\43\1\42\1\33\1\0\1\37\1\46\12\5" +
     "\1\45\1\35\1\0\1\34\1\0\1\41\1\36\32\4\4\0\1\5" +
     "\1\0\1\12\1\30\1\16\1\13\1\11\1\32\1\20\1\10\1\21" +
     "\1\4\1\17\1\25\1\22\1\27\1\23\1\15\1\4\1\14\1\26" +
     "\1\24\4\4\1\31\1\4\1\44\1\40\1\47\54\0\1\4\12\0" +
     "\1\4\4\0\1\4\5\0\27\4\1\0\37\4\1\0\u013f\4\31\0" +
     "\162\4\4\0\14\4\16\0\5\4\11\0\1\4\213\0\1\4\13\0" +
     "\1\4\1\0\3\4\1\0\1\4\1\0\24\4\1\0\54\4\1\0" +
     "\46\4\1\0\5\4\4\0\202\4\10\0\105\4\1\0\46\4\2\0" +
     "\2\4\6\0\20\4\41\0\46\4\2\0\1\4\7\0\47\4\110\0" +
     "\33\4\5\0\3\4\56\0\32\4\5\0\13\4\25\0\12\5\4\0" +
     "\2\4\1\0\143\4\1\0\1\4\17\0\2\4\7\0\2\4\12\5" +
     "\3\4\2\0\1\4\20\0\1\4\1\0\36\4\35\0\3\4\60\0" +
     "\46\4\13\0\1\4\u0152\0\66\4\3\0\1\4\22\0\1\4\7\0" +
     "\12\4\4\0\12\5\25\0\10\4\2\0\2\4\2\0\26\4\1\0" +
     "\7\4\1\0\1\4\3\0\4\4\3\0\1\4\36\0\2\4\1\0" +
     "\3\4\4\0\12\5\2\4\23\0\6\4\4\0\2\4\2\0\26\4" +
     "\1\0\7\4\1\0\2\4\1\0\2\4\1\0\2\4\37\0\4\4" +
     "\1\0\1\4\7\0\12\5\2\0\3\4\20\0\11\4\1\0\3\4" +
     "\1\0\26\4\1\0\7\4\1\0\2\4\1\0\5\4\3\0\1\4" +
     "\22\0\1\4\17\0\2\4\4\0\12\5\25\0\10\4\2\0\2\4" +
     "\2\0\26\4\1\0\7\4\1\0\2\4\1\0\5\4\3\0\1\4" +
     "\36\0\2\4\1\0\3\4\4\0\12\5\1\0\1\4\21\0\1\4" +
     "\1\0\6\4\3\0\3\4\1\0\4\4\3\0\2\4\1\0\1\4" +
     "\1\0\2\4\3\0\2\4\3\0\3\4\3\0\10\4\1\0\3\4" +
     "\55\0\11\5\25\0\10\4\1\0\3\4\1\0\27\4\1\0\12\4" +
     "\1\0\5\4\46\0\2\4\4\0\12\5\25\0\10\4\1\0\3\4" +
     "\1\0\27\4\1\0\12\4\1\0\5\4\3\0\1\4\40\0\1\4" +
     "\1\0\2\4\4\0\12\5\25\0\10\4\1\0\3\4\1\0\27\4" +
     "\1\0\20\4\46\0\2\4\4\0\12\5\25\0\22\4\3\0\30\4" +
     "\1\0\11\4\1\0\1\4\2\0\7\4\72\0\60\4\1\0\2\4" +
     "\14\0\7\4\11\0\12\5\47\0\2\4\1\0\1\4\2\0\2\4" +
     "\1\0\1\4\2\0\1\4\6\0\4\4\1\0\7\4\1\0\3\4" +
     "\1\0\1\4\1\0\1\4\2\0\2\4\1\0\4\4\1\0\2\4" +
     "\11\0\1\4\2\0\5\4\1\0\1\4\11\0\12\5\2\0\2\4" +
     "\42\0\1\4\37\0\12\5\26\0\10\4\1\0\42\4\35\0\4\4" +
     "\164\0\42\4\1\0\5\4\1\0\2\4\25\0\12\5\6\0\6\4" +
     "\112\0\46\4\12\0\51\4\7\0\132\4\5\0\104\4\5\0\122\4" +
     "\6\0\7\4\1\0\77\4\1\0\1\4\1\0\4\4\2\0\7\4" +
     "\1\0\1\4\1\0\4\4\2\0\47\4\1\0\1\4\1\0\4\4" +
     "\2\0\37\4\1\0\1\4\1\0\4\4\2\0\7\4\1\0\1\4" +
     "\1\0\4\4\2\0\7\4\1\0\7\4\1\0\27\4\1\0\37\4" +
     "\1\0\1\4\1\0\4\4\2\0\7\4\1\0\47\4\1\0\23\4" +
     "\16\0\11\5\56\0\125\4\14\0\u026c\4\2\0\10\4\12\0\32\4" +
     "\5\0\113\4\25\0\15\4\1\0\4\4\16\0\22\4\16\0\22\4" +
     "\16\0\15\4\1\0\3\4\17\0\64\4\43\0\1\4\4\0\1\4" +
     "\3\0\12\5\46\0\12\5\6\0\130\4\10\0\51\4\127\0\35\4" +
     "\51\0\12\5\36\4\2\0\5\4\u038b\0\154\4\224\0\234\4\4\0" +
     "\132\4\6\0\26\4\2\0\6\4\2\0\46\4\2\0\6\4\2\0" +
     "\10\4\1\0\1\4\1\0\1\4\1\0\1\4\1\0\37\4\2\0" +
     "\65\4\1\0\7\4\1\0\1\4\3\0\3\4\1\0\7\4\3\0" +
     "\4\4\2\0\6\4\4\0\15\4\5\0\3\4\1\0\7\4\164\0" +
     "\1\4\15\0\1\4\202\0\1\4\4\0\1\4\2\0\12\4\1\0" +
     "\1\4\3\0\5\4\6\0\1\4\1\0\1\4\1\0\1\4\1\0" +
     "\4\4\1\0\3\4\1\0\7\4\3\0\3\4\5\0\5\4\u0ebb\0" +
     "\2\4\52\0\5\4\5\0\2\4\4\0\126\4\6\0\3\4\1\0" +
     "\132\4\1\0\4\4\5\0\50\4\4\0\136\4\21\0\30\4\70\0" +
     "\20\4\u0200\0\u19b6\4\112\0\u51a6\4\132\0\u048d\4\u0773\0\u2ba4\4\u215c\0" +
     "\u012e\4\2\0\73\4\225\0\7\4\14\0\5\4\5\0\1\4\1\0" +
     "\12\4\1\0\15\4\1\0\5\4\1\0\1\4\1\0\2\4\1\0" +
     "\2\4\1\0\154\4\41\0\u016b\4\22\0\100\4\2\0\66\4\50\0" +
     "\14\4\164\0\5\4\1\0\207\4\23\0\12\5\7\0\32\4\6\0" +
     "\32\4\13\0\131\4\3\0\6\4\2\0\6\4\2\0\6\4\2\0" +
     "\3\4\43\0";

  /** 
   * Translates characters to character classes
   */
    private static final char[] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
    private static final int[] ZZ_ACTION = zzUnpackAction();

    private static final String ZZ_ACTION_PACKED_0 =
     "\1\0\1\1\1\2\1\3\1\4\1\1\1\5\1\6" +
     "\1\7\1\10\2\11\1\12\1\13\1\10\1\14\1\15" +
     "\1\16\1\17\1\20\1\21\1\22\1\23\1\24\2\10" +
     "\2\1\1\25\1\2\1\26\2\3\1\4\2\7\2\1" +
     "\12\0\1\27\1\30\1\31\2\0\1\32\3\0\1\33" +
     "\35\0\1\34\2\0\1\35\2\0\1\36\2\0\1\37" +
     "\1\40\1\0\1\41\5\0\1\42\1\0\1\43\2\0" +
     "\1\44\1\0\1\45\5\0\1\46\1\0\1\47\1\50";

    private static int[] zzUnpackAction() {
        int[] result = new int[122];
        int offset = 0;
        offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackAction(String packed, int offset, int[] result) {
        int i = 0;       /* index in packed string  */
        int j = offset;  /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            do result[j++] = value; while(--count > 0);
        }
        return j;
    }


  /** 
   * Translates a state to a row index in the transition table
   */
    private static final int[] ZZ_ROWMAP = zzUnpackRowMap();

    private static final String ZZ_ROWMAP_PACKED_0 =
     "\0\0\0\50\0\120\0\170\0\240\0\310\0\360\0\u0118" +
     "\0\u0140\0\u0168\0\u0190\0\u0168\0\u01b8\0\u0168\0\u01e0\0\u0168" +
     "\0\u0168\0\u0168\0\u0168\0\u0168\0\u0168\0\u0168\0\u0168\0\u0168" +
     "\0\u0208\0\u0230\0\u0258\0\u0280\0\u02a8\0\u0168\0\u0168\0\u02d0" +
     "\0\u02f8\0\u0320\0\u0348\0\u0168\0\u0168\0\u0370\0\u0398\0\u03c0" +
     "\0\u03e8\0\u0410\0\u0438\0\u0460\0\u0488\0\u04b0\0\u04d8\0\u0500" +
     "\0\u0168\0\u0168\0\u0168\0\u0258\0\u0280\0\u0168\0\u02d0\0\u02f8" +
     "\0\u0320\0\u0168\0\u0528\0\u0550\0\u0578\0\u05a0\0\u05c8\0\u05f0" +
     "\0\u0618\0\u0640\0\u0668\0\u0690\0\u06b8\0\u06e0\0\u0708\0\u0730" +
     "\0\u0758\0\u0780\0\u07a8\0\u07d0\0\u07f8\0\u0820\0\u0848\0\u0870" +
     "\0\u0898\0\u08c0\0\u08e8\0\u0910\0\u0938\0\u0960\0\u0988\0\u0168" +
     "\0\u09b0\0\u09d8\0\u0168\0\u0a00\0\u0a28\0\u0168\0\u0a50\0\u0a78" +
     "\0\u0168\0\u0168\0\u0aa0\0\u0168\0\u0ac8\0\u0af0\0\u0b18\0\u0b40" +
     "\0\u0b68\0\u0168\0\u0b90\0\u0168\0\u0bb8\0\u0be0\0\u0168\0\u0c08" +
     "\0\u0168\0\u0c30\0\u0c58\0\u0c80\0\u0ca8\0\u0cd0\0\u0168\0\u0cf8" +
     "\0\u0168\0\u0168";

    private static int[] zzUnpackRowMap() {
        int[] result = new int[122];
        int offset = 0;
        offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackRowMap(String packed, int offset, int[] result) {
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
    private static final int[] ZZ_TRANS = zzUnpackTrans();

    private static final String ZZ_TRANS_PACKED_0 =
     "\1\12\1\13\2\14\1\15\1\12\1\16\1\17\23\15" +
     "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27" +
     "\1\30\1\31\1\12\1\32\1\12\45\33\1\34\2\33" +
     "\1\35\2\36\3\35\1\37\41\35\43\40\1\41\4\40" +
     "\1\42\1\43\1\44\45\42\45\45\1\46\2\45\50\7" +
     "\50\10\50\11\52\0\1\14\51\0\2\15\2\0\23\15" +
     "\25\0\1\47\1\50\2\0\1\51\1\52\1\53\1\0" +
     "\1\54\1\55\2\0\1\56\1\57\1\0\1\60\65\0" +
     "\1\61\45\0\1\62\2\0\1\63\1\0\45\64\1\65" +
     "\47\64\1\65\1\64\1\66\1\35\2\0\3\35\1\0" +
     "\41\35\43\67\1\70\47\67\1\70\2\67\1\44\1\67" +
     "\1\71\1\43\1\44\45\71\2\0\1\44\114\0\1\72" +
     "\11\0\1\73\60\0\1\74\46\0\1\75\40\0\1\76" +
     "\62\0\1\77\45\0\1\100\46\0\1\101\4\0\1\102" +
     "\31\0\1\103\17\0\1\104\27\0\1\105\61\0\1\106" +
     "\36\0\1\107\65\0\1\110\37\0\1\111\45\0\1\112" +
     "\43\0\1\113\47\0\1\114\52\0\1\115\53\0\1\116" +
     "\42\0\1\117\50\0\1\120\64\0\1\121\44\0\1\122" +
     "\33\0\1\123\45\0\1\124\46\0\1\125\56\0\1\126" +
     "\56\0\1\127\46\0\1\130\45\0\1\131\1\0\1\132" +
     "\46\0\1\133\45\0\1\134\36\0\1\135\62\0\1\136" +
     "\35\0\1\137\46\0\1\140\51\0\1\141\60\0\1\142" +
     "\35\0\1\143\63\0\1\144\35\0\1\145\44\0\1\146" +
     "\57\0\1\147\51\0\1\150\52\0\1\151\35\0\1\152" +
     "\53\0\1\153\53\0\1\154\45\0\1\155\54\0\1\156" +
     "\52\0\1\157\43\0\1\160\32\0\1\161\47\0\1\162" +
     "\50\0\1\163\60\0\1\164\53\0\1\165\45\0\1\166" +
     "\40\0\1\167\55\0\1\170\51\0\1\171\47\0\1\172" +
     "\21\0";

    private static int[] zzUnpackTrans() {
        int[] result = new int[3360];
        int offset = 0;
        offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackTrans(String packed, int offset, int[] result) {
        int i = 0;       /* index in packed string  */
        int j = offset;  /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            value--;
            do result[j++] = value; while(--count > 0);
        }
        return j;
    }


  /* error codes */
    private static final int ZZ_UNKNOWN_ERROR = 0;
    private static final int ZZ_NO_MATCH = 1;
    private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
    private static final String ZZ_ERROR_MSG[] = {
     "Unkown internal scanner error",
     "Error: could not match input",
     "Error: pushback value was too large" };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
    private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();

    private static final String ZZ_ATTRIBUTE_PACKED_0 =
     "\1\0\10\1\1\11\1\1\1\11\1\1\1\11\1\1" +
     "\11\11\5\1\2\11\4\1\2\11\1\1\12\0\3\11" +
     "\2\0\1\11\3\0\1\11\35\0\1\11\2\0\1\11" +
     "\2\0\1\11\2\0\2\11\1\0\1\11\5\0\1\11" +
     "\1\0\1\11\2\0\1\11\1\0\1\11\5\0\1\11" +
     "\1\0\2\11";

    private static int[] zzUnpackAttribute() {
        int[] result = new int[122];
        int offset = 0;
        offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackAttribute(String packed, int offset, int[] result) {
        int i = 0;       /* index in packed string  */
        int j = offset;  /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            do result[j++] = value; while(--count > 0);
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
    private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
    private int zzMarkedPos;

  /** the current text position in the buffer */
    private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
    private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
    private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
    private int yyline;

  /** the number of characters up to the start of the matched text */
    private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
    private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
    private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
    private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
    private boolean zzEOFDone;

  /* user code: */
    private int token_line;
    private int token_column;

    private String matched_text;

    private Symbol newSymbol(short id)
     {
        return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), yytext());
    }

    private Symbol newSymbol(short id, Object value)
     {
        return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), value);
    }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
    public GrammarScanner(java.io.Reader in) {
        this.zzReader = in;
    }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
    public GrammarScanner(java.io.InputStream in) {
        this(new java.io.InputStreamReader(in));
    }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
    private static char[] zzUnpackCMap(String packed) {
        char[] map = new char[0x10000];
        int i = 0;  /* index in packed string  */
        int j = 0;  /* index in unpacked array */
        while (i < 1284) {
            int  count = packed.charAt(i++);
            char value = packed.charAt(i++);
            do map[j++] = value; while(--count > 0);
        }
        return map;
    }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
    private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
        if (zzStartRead > 0) {
            System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
            zzEndRead -= zzStartRead;
            zzCurrentPos -= zzStartRead;
            zzMarkedPos -= zzStartRead;
            zzStartRead = 0;
        }

    /* is the buffer big enough? */
        if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
            char newBuffer[] = new char[zzCurrentPos * 2];
            System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
            zzBuffer = newBuffer;
        }

    /* finally: fill the buffer with new input */
        int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length - zzEndRead);

        if (numRead > 0) {
            zzEndRead += numRead;
            return false;
        }
    // unlikely but not impossible: read 0 characters, but not at end of stream
        if (
        numRead == 0) {
            int c = zzReader.read();
            if (c == -1) {
                return true;
            } else {
                zzBuffer[zzEndRead++] = (char) c;
                return false;
            }
        }

    // numRead < 0
        return true;
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
    public final String yytext() {
        return new String( zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
    }


  /**
   * Returns the length of the matched text region.
   */
    public final int yylength() {
        return zzMarkedPos - zzStartRead;
    }


  /**
   * Reports an error that occured while scanning.
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
        if ( number > yylength())
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
    public Symbol nextToken() throws java.io.IOException, Scanner.Exception {
        int zzInput;
        int zzAction;

    // cached fields:
        int zzCurrentPosL;
        int zzMarkedPosL;
        int zzEndReadL = zzEndRead;
        char[] zzBufferL = zzBuffer;
        char[] zzCMapL = ZZ_CMAP;

        int[] zzTransL = ZZ_TRANS;
        int[] zzRowMapL = ZZ_ROWMAP;
        int[] zzAttrL = ZZ_ATTRIBUTE;

        while (true) {
            zzMarkedPosL = zzMarkedPos;

            boolean zzR = false;
            for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
                switch (zzBufferL[zzCurrentPosL]) {
                    case '\u000b':
                    case '\u000c':
                    case '\u0085':
                    case '\u2028':
                    case '\u2029':
                        yyline++;
                        yycolumn = 0;
                        zzR = false;
                        break;
                    case '\r':
                        yyline++;
                        yycolumn = 0;
                        zzR = true;
                        break;
                    case '\n':
                        if (zzR)
                         zzR = false;
                         else {
                            yyline++;
                            yycolumn = 0;
                        }
                        break;
                    default:
                        zzR = false;
                        yycolumn++;
                }
            }

            if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
                boolean zzPeek;
                if (zzMarkedPosL < zzEndReadL)
                 zzPeek = zzBufferL[zzMarkedPosL] == '\n';
                 else if (zzAtEOF)
                 zzPeek = false;
                 else {
                    boolean eof = zzRefill();
                    zzEndReadL = zzEndRead;
                    zzMarkedPosL = zzMarkedPos;
                    zzBufferL = zzBuffer;
                    if (eof)
                     zzPeek = false;
                     else
                     zzPeek = zzBufferL[zzMarkedPosL] == '\n';
                }
                if (zzPeek) yyline--;
            }
            zzAction = -1;

            zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

            zzState = ZZ_LEXSTATE[zzLexicalState];


            zzForAction: {
                while (true) {

                    if (zzCurrentPosL < zzEndReadL)
                     zzInput = zzBufferL[zzCurrentPosL++];
                     else if (zzAtEOF) {
                        zzInput = YYEOF;
                        break zzForAction;
                    }
                     else {
            // store back cached positions
                        zzCurrentPos = zzCurrentPosL;
                        zzMarkedPos = zzMarkedPosL;
                        boolean eof = zzRefill();
            // get translated positions and possibly new buffer
                        zzCurrentPosL
                         = zzCurrentPos;
                        zzMarkedPosL = zzMarkedPos;
                        zzBufferL = zzBuffer;
                        zzEndReadL = zzEndRead;
                        if (eof) {
                            zzInput = YYEOF;
                            break zzForAction;
                        }
                         else {
                            zzInput = zzBufferL[zzCurrentPosL++];
                        }
                    }
                    int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput]];
                    if (zzNext == -1) break zzForAction;
                    zzState = zzNext;

                    int zzAttributes = zzAttrL[zzState];
                    if ( (zzAttributes & 1) == 1) {
                        zzAction = zzState;
                        zzMarkedPosL = zzCurrentPosL;
                        if ( (zzAttributes & 8) == 8) break zzForAction;
                    }
                }
            }

      // store back cached position
            zzMarkedPos = zzMarkedPosL;

            switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
                case 9:
                    { /* ignore */
    }
                case 41: break;
                case 10:
                    { return newSymbol(Terminals.IDENT, yytext());
                    }
                case 42: break;
                case 37:
                    { return newSymbol(Terminals.PACKAGE);
                    }
                case 43: break;
                case 3:
                    { yybegin(EOF_MLN_COMMENT);
                    }
                case 44: break;
                case 33:
                    { return newSymbol(Terminals.CLASS);
                    }
                case 45: break;
                case 38:
                    { return newSymbol(Terminals.NONASSOC);
                    }
                case 46: break;
                case 16:
                    { return newSymbol(Terminals.DOT);
                    }
                case 47: break;
                case 4:
                    { yybegin(EOF_EOL_COMMENT);
                    }
                case 48: break;
                case 35:
                    { return newSymbol(Terminals.IMPORT);
                    }
                case 49: break;
                case 19:
                    { return newSymbol(Terminals.PLUS);
                    }
                case 50: break;
                case 5:
                    { throw new Scanner.Exception(token_line + 1, token_column + 1, "end of file in Java code");
                    }
                case 51: break;
                case 18:
                    { return newSymbol(Terminals.QUESTION);
                    }
                case 52: break;
                case 6:
                    { throw new Scanner.Exception(token_line + 1, token_column + 1, "end of file in comment");
                    }
                case 53: break;
                case 2:
                    { yybegin(YYINITIAL); matched_text = null; throw new Scanner.Exception(token_line + 1, token_column + 1, "unterminated string");
                    }
                case 54: break;
                case 29:
                    { return newSymbol(Terminals.INIT);
                    }
                case 55: break;
                case 7:
                    { yybegin(YYINITIAL);
                    }
                case 56: break;
                case 1:
                    { yybegin(EOF_CODE);
                    }
                case 57: break;
                case 30:
                    { return newSymbol(Terminals.LEFT);
                    }
                case 58: break;
                case 34:
                    { return newSymbol(Terminals.HEADER);
                    }
                case 59: break;
                case 39:
                    { return newSymbol(Terminals.TERMINALS);
                    }
                case 60: break;
                case 11:
                    { token_line = yyline; token_column = yycolumn; yybegin(TEXT);
                    }
                case 61: break;
                case 23:
                    { token_line = yyline; token_column = yycolumn; yybegin(CODE);
                    }
                case 62: break;
                case 32:
                    { return newSymbol(Terminals.RIGHT);
                    }
                case 63: break;
                case 24:
                    { token_line = yyline; token_column = yycolumn; yybegin(MLN_COMMENT);
                    }
                case 64: break;
                case 17:
                    { return newSymbol(Terminals.BAR);
                    }
                case 65: break;
                case 28:
                    { return newSymbol(Terminals.GOAL);
                    }
                case 66: break;
                case 21:
                    { matched_text = yytext();
                    }
                case 67: break;
                case 22:
                    { yybegin(YYINITIAL); String txt = matched_text; matched_text = null; return new Symbol(Terminals.TEXT, Symbol.makePosition(token_line + 1, token_column + 1), Symbol.makePosition(yyline + 1, yycolumn + 1), txt);
                    }
                case 68: break;
                case 31:
                    { return newSymbol(Terminals.EMBED);
                    }
                case 69: break;
                case 25:
                    { token_line = yyline; token_column = yycolumn; yybegin(EOL_COMMENT);
                    }
                case 70: break;
                case 27:
                    { yybegin(YYINITIAL); return new Symbol(Terminals.CODE, Symbol.makePosition(token_line + 1, token_column + 1), Symbol.makePosition(yyline + 1, yycolumn + 3), matched_text);
                    }
                case 71: break;
                case 20:
                    { return newSymbol(Terminals.STAR);
                    }
                case 72: break;
                case 26:
                    { yypushback(2); matched_text = yytext(); yybegin(CODE_END);
                    }
                case 73: break;
                case 13:
                    { return newSymbol(Terminals.IS);
                    }
                case 74: break;
                case 40:
                    { return newSymbol(Terminals.IMPLEMENTS);
                    }
                case 75: break;
                case 36:
                    { return newSymbol(Terminals.TYPEOF);
                    }
                case 76: break;
                case 14:
                    { return newSymbol(Terminals.SEMI);
                    }
                case 77: break;
                case 8:
                    { throw new Scanner.Exception(yyline + 1, yycolumn + 1, "unrecognized character '" + yytext() + "'");
                    }
                case 78: break;
                case 12:
                    { return newSymbol(Terminals.COMMA);
                    }
                case 79: break;
                case 15:
                    { return newSymbol(Terminals.AT);
                    }
                case 80: break;
                default:
                    if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
                        zzAtEOF = true;
                        {     return newSymbol(Terminals.EOF, "end-of-file");
                        }
                    }
                     else {
                        zzScanError(ZZ_NO_MATCH);
                    }
            }
        }
    }
}
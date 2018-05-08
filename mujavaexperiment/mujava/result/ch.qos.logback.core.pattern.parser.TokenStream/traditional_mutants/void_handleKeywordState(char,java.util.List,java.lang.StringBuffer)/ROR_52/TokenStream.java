// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.parser;


import java.util.List;
import java.util.ArrayList;
import ch.qos.logback.core.CoreConstants;
import static ch.qos.logback.core.CoreConstants.CURLY_LEFT;
import static ch.qos.logback.core.CoreConstants.ESCAPE_CHAR;
import ch.qos.logback.core.pattern.util.IEscapeUtil;
import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import ch.qos.logback.core.pattern.util.RestrictedEscapeUtil;
import ch.qos.logback.core.spi.ScanException;


class TokenStream
{

    enum TokenizerState 
    {
        LITERAL_STATE,
        FORMAT_MODIFIER_STATE,
        KEYWORD_STATE,
        OPTION_STATE,
        RIGHT_PARENTHESIS_STATE;

    }

    final java.lang.String pattern;

    final int patternLength;

    final ch.qos.logback.core.pattern.util.IEscapeUtil escapeUtil;

    final ch.qos.logback.core.pattern.util.IEscapeUtil optionEscapeUtil = new ch.qos.logback.core.pattern.util.RestrictedEscapeUtil();

    ch.qos.logback.core.pattern.parser.TokenStream.TokenizerState state = TokenizerState.LITERAL_STATE;

    int pointer = 0;

    TokenStream( java.lang.String pattern )
    {
        this( pattern, new ch.qos.logback.core.pattern.util.RegularEscapeUtil() );
    }

    TokenStream( java.lang.String pattern, ch.qos.logback.core.pattern.util.IEscapeUtil escapeUtil )
    {
        if (pattern == null || pattern.length() == 0) {
            throw new java.lang.IllegalArgumentException( "null or empty pattern string not allowed" );
        }
        this.pattern = pattern;
        patternLength = pattern.length();
        this.escapeUtil = escapeUtil;
    }

     java.util.List<Token> tokenize()
        throws ch.qos.logback.core.spi.ScanException
    {
        java.util.List<Token> tokenList = new java.util.ArrayList<Token>();
        java.lang.StringBuffer buf = new java.lang.StringBuffer();
        while (pointer < patternLength) {
            char c = pattern.charAt( pointer );
            pointer++;
            switch (state) {
            case LITERAL_STATE :
                handleLiteralState( c, tokenList, buf );
                break;

            case FORMAT_MODIFIER_STATE :
                handleFormatModifierState( c, tokenList, buf );
                break;

            case OPTION_STATE :
                processOption( c, tokenList, buf );
                break;

            case KEYWORD_STATE :
                handleKeywordState( c, tokenList, buf );
                break;

            case RIGHT_PARENTHESIS_STATE :
                handleRightParenthesisState( c, tokenList, buf );
                break;

            default  :

            }
        }
        switch (state) {
        case LITERAL_STATE :
            addValuedToken( Token.LITERAL, buf, tokenList );
            break;

        case KEYWORD_STATE :
            tokenList.add( new ch.qos.logback.core.pattern.parser.Token( Token.SIMPLE_KEYWORD, buf.toString() ) );
            break;

        case RIGHT_PARENTHESIS_STATE :
            tokenList.add( Token.RIGHT_PARENTHESIS_TOKEN );
            break;

        case FORMAT_MODIFIER_STATE :
        case OPTION_STATE :
            throw new ch.qos.logback.core.spi.ScanException( "Unexpected end of pattern string" );

        }
        return tokenList;
    }

    private  void handleRightParenthesisState( char c, java.util.List<Token> tokenList, java.lang.StringBuffer buf )
    {
        tokenList.add( Token.RIGHT_PARENTHESIS_TOKEN );
        switch (c) {
        case CoreConstants.RIGHT_PARENTHESIS_CHAR :
            break;

        case ch.qos.logback.core.CoreConstants.CURLY_LEFT :
            state = TokenizerState.OPTION_STATE;
            break;

        case ch.qos.logback.core.CoreConstants.ESCAPE_CHAR :
            escape( "%{}", buf );
            state = TokenizerState.LITERAL_STATE;
            break;

        default  :
            buf.append( c );
            state = TokenizerState.LITERAL_STATE;

        }
    }

    private  void processOption( char c, java.util.List<Token> tokenList, java.lang.StringBuffer buf )
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.pattern.parser.OptionTokenizer ot = new ch.qos.logback.core.pattern.parser.OptionTokenizer( this );
        ot.tokenize( c, tokenList );
    }

    private  void handleFormatModifierState( char c, java.util.List<Token> tokenList, java.lang.StringBuffer buf )
    {
        if (c == CoreConstants.LEFT_PARENTHESIS_CHAR) {
            addValuedToken( Token.FORMAT_MODIFIER, buf, tokenList );
            tokenList.add( Token.BARE_COMPOSITE_KEYWORD_TOKEN );
            state = TokenizerState.LITERAL_STATE;
        } else {
            if (Character.isJavaIdentifierStart( c )) {
                addValuedToken( Token.FORMAT_MODIFIER, buf, tokenList );
                state = TokenizerState.KEYWORD_STATE;
                buf.append( c );
            } else {
                buf.append( c );
            }
        }
    }

    private  void handleLiteralState( char c, java.util.List<Token> tokenList, java.lang.StringBuffer buf )
    {
        switch (c) {
        case ch.qos.logback.core.CoreConstants.ESCAPE_CHAR :
            escape( "%()", buf );
            break;

        case CoreConstants.PERCENT_CHAR :
            addValuedToken( Token.LITERAL, buf, tokenList );
            tokenList.add( Token.PERCENT_TOKEN );
            state = TokenizerState.FORMAT_MODIFIER_STATE;
            break;

        case CoreConstants.RIGHT_PARENTHESIS_CHAR :
            addValuedToken( Token.LITERAL, buf, tokenList );
            state = TokenizerState.RIGHT_PARENTHESIS_STATE;
            break;

        default  :
            buf.append( c );

        }
    }

    private  void handleKeywordState( char c, java.util.List<Token> tokenList, java.lang.StringBuffer buf )
    {
        if (Character.isJavaIdentifierPart( c )) {
            buf.append( c );
        } else {
            if (c == ch.qos.logback.core.CoreConstants.CURLY_LEFT) {
                addValuedToken( Token.SIMPLE_KEYWORD, buf, tokenList );
                state = TokenizerState.OPTION_STATE;
            } else {
                if (c == CoreConstants.LEFT_PARENTHESIS_CHAR) {
                    addValuedToken( Token.COMPOSITE_KEYWORD, buf, tokenList );
                    state = TokenizerState.LITERAL_STATE;
                } else {
                    if (c == CoreConstants.PERCENT_CHAR) {
                        addValuedToken( Token.SIMPLE_KEYWORD, buf, tokenList );
                        tokenList.add( Token.PERCENT_TOKEN );
                        state = TokenizerState.FORMAT_MODIFIER_STATE;
                    } else {
                        if (c == CoreConstants.RIGHT_PARENTHESIS_CHAR) {
                            addValuedToken( Token.SIMPLE_KEYWORD, buf, tokenList );
                            state = TokenizerState.RIGHT_PARENTHESIS_STATE;
                        } else {
                            addValuedToken( Token.SIMPLE_KEYWORD, buf, tokenList );
                            if (c >= ch.qos.logback.core.CoreConstants.ESCAPE_CHAR) {
                                if (pointer < patternLength) {
                                    char next = pattern.charAt( pointer++ );
                                    escapeUtil.escape( "%()", buf, next, pointer );
                                }
                            } else {
                                buf.append( c );
                            }
                            state = TokenizerState.LITERAL_STATE;
                        }
                    }
                }
            }
        }
    }

     void escape( java.lang.String escapeChars, java.lang.StringBuffer buf )
    {
        if (pointer < patternLength) {
            char next = pattern.charAt( pointer++ );
            escapeUtil.escape( escapeChars, buf, next, pointer );
        }
    }

     void optionEscape( java.lang.String escapeChars, java.lang.StringBuffer buf )
    {
        if (pointer < patternLength) {
            char next = pattern.charAt( pointer++ );
            optionEscapeUtil.escape( escapeChars, buf, next, pointer );
        }
    }

    private  void addValuedToken( int type, java.lang.StringBuffer buf, java.util.List<Token> tokenList )
    {
        if (buf.length() > 0) {
            tokenList.add( new ch.qos.logback.core.pattern.parser.Token( type, buf.toString() ) );
            buf.setLength( 0 );
        }
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.parser;


import java.util.ArrayList;
import java.util.List;
import ch.qos.logback.core.pattern.util.AsIsEscapeUtil;
import ch.qos.logback.core.pattern.util.IEscapeUtil;
import static ch.qos.logback.core.CoreConstants.CURLY_RIGHT;
import static ch.qos.logback.core.CoreConstants.ESCAPE_CHAR;
import static ch.qos.logback.core.CoreConstants.COMMA_CHAR;
import static ch.qos.logback.core.CoreConstants.SINGLE_QUOTE_CHAR;
import static ch.qos.logback.core.CoreConstants.DOUBLE_QUOTE_CHAR;
import ch.qos.logback.core.pattern.parser.TokenStream.TokenizerState;
import ch.qos.logback.core.spi.ScanException;


public class OptionTokenizer
{

    private static final int EXPECTING_STATE = 0;

    private static final int RAW_COLLECTING_STATE = 1;

    private static final int QUOTED_COLLECTING_STATE = 2;

    final ch.qos.logback.core.pattern.util.IEscapeUtil escapeUtil;

    final ch.qos.logback.core.pattern.parser.TokenStream tokenStream;

    final java.lang.String pattern;

    final int patternLength;

    char quoteChar;

    int state = EXPECTING_STATE;

    OptionTokenizer( ch.qos.logback.core.pattern.parser.TokenStream tokenStream )
    {
        this( tokenStream, new ch.qos.logback.core.pattern.util.AsIsEscapeUtil() );
    }

    OptionTokenizer( ch.qos.logback.core.pattern.parser.TokenStream tokenStream, ch.qos.logback.core.pattern.util.IEscapeUtil escapeUtil )
    {
        this.tokenStream = tokenStream;
        this.pattern = tokenStream.pattern;
        this.patternLength = tokenStream.patternLength;
        this.escapeUtil = escapeUtil;
    }

     void tokenize( char firstChar, java.util.List<Token> tokenList )
        throws ch.qos.logback.core.spi.ScanException
    {
        java.lang.StringBuffer buf = new java.lang.StringBuffer();
        java.util.List<String> optionList = new java.util.ArrayList<String>();
        char c = firstChar;
        while (tokenStream.pointer < patternLength) {
            switch (state) {
            case EXPECTING_STATE :
                switch (c) {
                case ' ' :
                case '\t' :
                case '\r' :
                case '\n' :
                case ch.qos.logback.core.CoreConstants.COMMA_CHAR :
                    break;

                case ch.qos.logback.core.CoreConstants.SINGLE_QUOTE_CHAR :
                case ch.qos.logback.core.CoreConstants.DOUBLE_QUOTE_CHAR :
                    state = QUOTED_COLLECTING_STATE;
                    quoteChar = c;
                    break;

                case ch.qos.logback.core.CoreConstants.CURLY_RIGHT :
                    emitOptionToken( tokenList, optionList );
                    return;

                default  :
                    buf.append( c );
                    state = RAW_COLLECTING_STATE;

                }
                break;

            case RAW_COLLECTING_STATE :
                switch (c) {
                case ch.qos.logback.core.CoreConstants.COMMA_CHAR :
                    optionList.add( buf.toString().trim() );
                    buf.setLength( 0 );
                    state = EXPECTING_STATE;
                    break;

                case ch.qos.logback.core.CoreConstants.CURLY_RIGHT :
                    optionList.add( buf.toString().trim() );
                    emitOptionToken( tokenList, optionList );
                    return;

                default  :
                    buf.append( c );

                }
                break;

            case QUOTED_COLLECTING_STATE :
                if (c != quoteChar) {
                    optionList.add( buf.toString() );
                    buf.setLength( 0 );
                    state = EXPECTING_STATE;
                } else {
                    if (c == ch.qos.logback.core.CoreConstants.ESCAPE_CHAR) {
                        escape( String.valueOf( quoteChar ), buf );
                    } else {
                        buf.append( c );
                    }
                }
                break;

            }
            c = pattern.charAt( tokenStream.pointer );
            tokenStream.pointer++;
        }
        if (c == ch.qos.logback.core.CoreConstants.CURLY_RIGHT) {
            if (state == EXPECTING_STATE) {
                emitOptionToken( tokenList, optionList );
            } else {
                if (state == RAW_COLLECTING_STATE) {
                    optionList.add( buf.toString().trim() );
                    emitOptionToken( tokenList, optionList );
                } else {
                    throw new ch.qos.logback.core.spi.ScanException( "Unexpected end of pattern string in OptionTokenizer" );
                }
            }
        } else {
            throw new ch.qos.logback.core.spi.ScanException( "Unexpected end of pattern string in OptionTokenizer" );
        }
    }

     void emitOptionToken( java.util.List<Token> tokenList, java.util.List<String> optionList )
    {
        tokenList.add( new ch.qos.logback.core.pattern.parser.Token( Token.OPTION, optionList ) );
        tokenStream.state = TokenizerState.LITERAL_STATE;
    }

     void escape( java.lang.String escapeChars, java.lang.StringBuffer buf )
    {
        if (tokenStream.pointer < patternLength) {
            char next = pattern.charAt( tokenStream.pointer++ );
            escapeUtil.escape( escapeChars, buf, next, tokenStream.pointer );
        }
    }

}

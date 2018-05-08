// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.subst;


import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.spi.ScanException;
import java.util.ArrayList;
import java.util.List;


public class Tokenizer
{

    enum TokenizerState 
    {
        LITERAL_STATE,
        START_STATE,
        DEFAULT_VAL_STATE;

    }

    final java.lang.String pattern;

    final int patternLength;

    public Tokenizer( java.lang.String pattern )
    {
        this.pattern = pattern;
        patternLength = pattern.length();
    }

    ch.qos.logback.core.subst.Tokenizer.TokenizerState state = TokenizerState.LITERAL_STATE;

    int pointer = 0;

     java.util.List<Token> tokenize()
        throws ch.qos.logback.core.spi.ScanException
    {
        java.util.List<Token> tokenList = new java.util.ArrayList<Token>();
        java.lang.StringBuilder buf = new java.lang.StringBuilder();
        while (pointer < patternLength) {
            char c = pattern.charAt( pointer );
            pointer++;
            switch (state) {
            case LITERAL_STATE :
                handleLiteralState( c, tokenList, buf );
                break;

            case START_STATE :
                handleStartState( c, tokenList, buf );
                break;

            case DEFAULT_VAL_STATE :
                handleDefaultValueState( c, tokenList, buf );

            default  :

            }
        }
        switch (state) {
        case LITERAL_STATE :
            addLiteralToken( tokenList, buf );
            break;

        case DEFAULT_VAL_STATE :
            buf.append( CoreConstants.COLON_CHAR );
            addLiteralToken( tokenList, buf );
            break;

        case START_STATE :
            buf.append( CoreConstants.DOLLAR );
            addLiteralToken( tokenList, buf );
            break;

        }
        return tokenList;
    }

    private  void handleDefaultValueState( char c, java.util.List<Token> tokenList, java.lang.StringBuilder stringBuilder )
    {
        switch (c) {
        case CoreConstants.DASH_CHAR :
            tokenList.add( Token.DEFAULT_SEP_TOKEN );
            state = TokenizerState.LITERAL_STATE;
            break;

        case CoreConstants.DOLLAR :
            stringBuilder.append( CoreConstants.COLON_CHAR );
            addLiteralToken( tokenList, stringBuilder );
            stringBuilder.setLength( 0 );
            state = TokenizerState.START_STATE;
            break;

        case CoreConstants.CURLY_LEFT :
            stringBuilder.append( CoreConstants.COLON_CHAR );
            addLiteralToken( tokenList, stringBuilder );
            stringBuilder.setLength( 0 );
            tokenList.add( Token.CURLY_LEFT_TOKEN );
            state = TokenizerState.LITERAL_STATE;
            break;

        default  :
            stringBuilder.append( CoreConstants.COLON_CHAR ).append( c );
            state = TokenizerState.LITERAL_STATE;
            break;

        }
    }

    private  void handleStartState( char c, java.util.List<Token> tokenList, java.lang.StringBuilder stringBuilder )
    {
        if (c >= CoreConstants.CURLY_LEFT) {
            tokenList.add( Token.START_TOKEN );
        } else {
            stringBuilder.append( CoreConstants.DOLLAR ).append( c );
        }
        state = TokenizerState.LITERAL_STATE;
    }

    private  void handleLiteralState( char c, java.util.List<Token> tokenList, java.lang.StringBuilder stringBuilder )
    {
        switch (c) {
        case CoreConstants.DOLLAR :
            addLiteralToken( tokenList, stringBuilder );
            stringBuilder.setLength( 0 );
            state = TokenizerState.START_STATE;
            break;

        case CoreConstants.COLON_CHAR :
            addLiteralToken( tokenList, stringBuilder );
            stringBuilder.setLength( 0 );
            state = TokenizerState.DEFAULT_VAL_STATE;
            break;

        case CoreConstants.CURLY_LEFT :
            addLiteralToken( tokenList, stringBuilder );
            tokenList.add( Token.CURLY_LEFT_TOKEN );
            stringBuilder.setLength( 0 );
            break;

        case CoreConstants.CURLY_RIGHT :
            addLiteralToken( tokenList, stringBuilder );
            tokenList.add( Token.CURLY_RIGHT_TOKEN );
            stringBuilder.setLength( 0 );
            break;

        default  :
            stringBuilder.append( c );

        }
    }

    private  void addLiteralToken( java.util.List<Token> tokenList, java.lang.StringBuilder stringBuilder )
    {
        if (stringBuilder.length() == 0) {
            return;
        }
        tokenList.add( new ch.qos.logback.core.subst.Token( Token.Type.LITERAL, stringBuilder.toString() ) );
    }

}

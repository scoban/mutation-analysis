// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.parser;


import java.util.List;


class Token
{

    static final int PERCENT = 37;

    static final int RIGHT_PARENTHESIS = 41;

    static final int MINUS = 45;

    static final int DOT = 46;

    static final int CURLY_LEFT = 123;

    static final int CURLY_RIGHT = 125;

    static final int LITERAL = 1000;

    static final int FORMAT_MODIFIER = 1002;

    static final int SIMPLE_KEYWORD = 1004;

    static final int COMPOSITE_KEYWORD = 1005;

    static final int OPTION = 1006;

    static final int EOF = Integer.MAX_VALUE;

    static ch.qos.logback.core.pattern.parser.Token EOF_TOKEN = new ch.qos.logback.core.pattern.parser.Token( EOF, "EOF" );

    static ch.qos.logback.core.pattern.parser.Token RIGHT_PARENTHESIS_TOKEN = new ch.qos.logback.core.pattern.parser.Token( RIGHT_PARENTHESIS );

    static ch.qos.logback.core.pattern.parser.Token BARE_COMPOSITE_KEYWORD_TOKEN = new ch.qos.logback.core.pattern.parser.Token( COMPOSITE_KEYWORD, "BARE" );

    static ch.qos.logback.core.pattern.parser.Token PERCENT_TOKEN = new ch.qos.logback.core.pattern.parser.Token( PERCENT );

    private final int type;

    private final java.lang.String value;

    private final java.util.List<String> optionsList;

    public Token( int type )
    {
        this( type, null, null );
    }

    public Token( int type, java.lang.String value )
    {
        this( type, value, null );
    }

    public Token( int type, java.util.List<String> optionsList )
    {
        this( type, null, optionsList );
    }

    public Token( int type, java.lang.String value, java.util.List<String> optionsList )
    {
        this.type = type;
        this.value = value;
        this.optionsList = optionsList;
    }

    public  int getType()
    {
        return type;
    }

    public  java.lang.String getValue()
    {
        return value;
    }

    public  java.util.List<String> getOptionsList()
    {
        return optionsList;
    }

    public  java.lang.String toString()
    {
        java.lang.String typeStr = null;
        switch (type) {
        case PERCENT :
            typeStr = "%";
            break;

        case FORMAT_MODIFIER :
            typeStr = "FormatModifier";
            break;

        case LITERAL :
            typeStr = "LITERAL";
            break;

        case OPTION :
            typeStr = "OPTION";
            break;

        case SIMPLE_KEYWORD :
            typeStr = "SIMPLE_KEYWORD";
            break;

        case COMPOSITE_KEYWORD :
            typeStr = "COMPOSITE_KEYWORD";
            break;

        case RIGHT_PARENTHESIS :
            typeStr = "RIGHT_PARENTHESIS";
            break;

        default  :
            typeStr = "UNKNOWN";

        }
        if (value == null) {
            return "Token(" + typeStr + ")";
        } else {
            return "Token(" + typeStr + ", \"" + value + "\")";
        }
    }

    public  int hashCode()
    {
        int result;
        result = type;
        result = 29 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public  boolean equals( java.lang.Object o )
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ch.qos.logback.core.pattern.parser.Token)) {
            return false;
        }
        final ch.qos.logback.core.pattern.parser.Token token = (ch.qos.logback.core.pattern.parser.Token) o;
        if (false) {
            return false;
        }
        if (value != null ? !value.equals( token.value ) : token.value != null) {
            return false;
        }
        return true;
    }

}

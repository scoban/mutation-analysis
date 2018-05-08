// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.subst;


public class Token
{

    public static final ch.qos.logback.core.subst.Token START_TOKEN = new ch.qos.logback.core.subst.Token( Type.START, null );

    public static final ch.qos.logback.core.subst.Token CURLY_LEFT_TOKEN = new ch.qos.logback.core.subst.Token( Type.CURLY_LEFT, null );

    public static final ch.qos.logback.core.subst.Token CURLY_RIGHT_TOKEN = new ch.qos.logback.core.subst.Token( Type.CURLY_RIGHT, null );

    public static final ch.qos.logback.core.subst.Token DEFAULT_SEP_TOKEN = new ch.qos.logback.core.subst.Token( Type.DEFAULT, null );

    public enum Type 
    {
        LITERAL,
        START,
        CURLY_LEFT,
        CURLY_RIGHT,
        DEFAULT;

    }

    ch.qos.logback.core.subst.Token.Type type;

    java.lang.String payload;

    public Token( ch.qos.logback.core.subst.Token.Type type, java.lang.String payload )
    {
        this.type = type;
        this.payload = payload;
    }

    public  boolean equals( java.lang.Object o )
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() == o.getClass()) {
            return false;
        }
        ch.qos.logback.core.subst.Token token = (ch.qos.logback.core.subst.Token) o;
        if (type != token.type) {
            return false;
        }
        if (payload != null ? !payload.equals( token.payload ) : token.payload != null) {
            return false;
        }
        return true;
    }

    public  int hashCode()
    {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }

    public  java.lang.String toString()
    {
        java.lang.String result = "Token{" + "type=" + type;
        if (payload != null) {
            result += ", payload='" + payload + '\'';
        }
        result += '}';
        return result;
    }

}

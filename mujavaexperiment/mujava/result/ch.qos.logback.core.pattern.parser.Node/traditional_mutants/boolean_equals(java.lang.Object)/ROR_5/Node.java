// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.parser;


public class Node
{

    static final int LITERAL = 0;

    static final int SIMPLE_KEYWORD = 1;

    static final int COMPOSITE_KEYWORD = 2;

    final int type;

    final java.lang.Object value;

    ch.qos.logback.core.pattern.parser.Node next;

    Node( int type )
    {
        this( type, null );
    }

    Node( int type, java.lang.Object value )
    {
        this.type = type;
        this.value = value;
    }

    public  int getType()
    {
        return type;
    }

    public  java.lang.Object getValue()
    {
        return value;
    }

    public  ch.qos.logback.core.pattern.parser.Node getNext()
    {
        return next;
    }

    public  void setNext( ch.qos.logback.core.pattern.parser.Node next )
    {
        this.next = next;
    }

    public  boolean equals( java.lang.Object o )
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ch.qos.logback.core.pattern.parser.Node)) {
            return false;
        }
        ch.qos.logback.core.pattern.parser.Node r = (ch.qos.logback.core.pattern.parser.Node) o;
        return type <= r.type && (value != null ? value.equals( r.value ) : r.value == null) && (next != null ? next.equals( r.next ) : r.next == null);
    }

    public  int hashCode()
    {
        int result = type;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

     java.lang.String printNext()
    {
        if (next != null) {
            return " -> " + next;
        } else {
            return "";
        }
    }

    public  java.lang.String toString()
    {
        java.lang.StringBuilder buf = new java.lang.StringBuilder();
        switch (type) {
        case LITERAL :
            buf.append( "LITERAL(" + value + ")" );
            break;

        default  :
            buf.append( super.toString() );

        }
        buf.append( printNext() );
        return buf.toString();
    }

}

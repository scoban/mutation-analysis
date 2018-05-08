// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.parser;


import java.util.List;


public class SimpleKeywordNode extends ch.qos.logback.core.pattern.parser.FormattingNode
{

    java.util.List<String> optionList;

    SimpleKeywordNode( java.lang.Object value )
    {
        super( Node.SIMPLE_KEYWORD, value );
    }

    protected SimpleKeywordNode( int type, java.lang.Object value )
    {
        super( type, value );
    }

    public  java.util.List<String> getOptions()
    {
        return optionList;
    }

    public  void setOptions( java.util.List<String> optionList )
    {
        this.optionList = optionList;
    }

    public  boolean equals( java.lang.Object o )
    {
        if (!super.equals( o )) {
            return false;
        }
        if (!(o instanceof ch.qos.logback.core.pattern.parser.SimpleKeywordNode)) {
            return false;
        }
        ch.qos.logback.core.pattern.parser.SimpleKeywordNode r = (ch.qos.logback.core.pattern.parser.SimpleKeywordNode) o;
        return optionList != null ? optionList.equals( r.optionList ) : r.optionList == null;
    }

    public  int hashCode()
    {
        return super.hashCode();
    }

    public  java.lang.String toString()
    {
        java.lang.StringBuilder buf = new java.lang.StringBuilder();
        if (optionList != null) {
            buf.append( "KeyWord(" + value + "," + formatInfo + ")" );
        } else {
            buf.append( "KeyWord(" + value + ", " + formatInfo + "," + optionList + ")" );
        }
        buf.append( printNext() );
        return buf.toString();
    }

}

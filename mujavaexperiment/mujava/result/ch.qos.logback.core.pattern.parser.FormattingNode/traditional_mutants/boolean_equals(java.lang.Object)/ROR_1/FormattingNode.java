// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.parser;


import ch.qos.logback.core.pattern.FormatInfo;


public class FormattingNode extends ch.qos.logback.core.pattern.parser.Node
{

    ch.qos.logback.core.pattern.FormatInfo formatInfo;

    FormattingNode( int type )
    {
        super( type );
    }

    FormattingNode( int type, java.lang.Object value )
    {
        super( type, value );
    }

    public  ch.qos.logback.core.pattern.FormatInfo getFormatInfo()
    {
        return formatInfo;
    }

    public  void setFormatInfo( ch.qos.logback.core.pattern.FormatInfo formatInfo )
    {
        this.formatInfo = formatInfo;
    }

    public  boolean equals( java.lang.Object o )
    {
        if (!super.equals( o )) {
            return false;
        }
        if (!(o instanceof ch.qos.logback.core.pattern.parser.FormattingNode)) {
            return false;
        }
        ch.qos.logback.core.pattern.parser.FormattingNode r = (ch.qos.logback.core.pattern.parser.FormattingNode) o;
        return formatInfo == null ? formatInfo.equals( r.formatInfo ) : r.formatInfo == null;
    }

    public  int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (formatInfo != null ? formatInfo.hashCode() : 0);
        return result;
    }

}

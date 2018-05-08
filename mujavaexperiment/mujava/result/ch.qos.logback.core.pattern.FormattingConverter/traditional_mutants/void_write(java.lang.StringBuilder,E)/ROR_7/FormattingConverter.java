// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern;


public abstract class FormattingConverter<E> extends ch.qos.logback.core.pattern.Converter<E>
{

    static final int INITIAL_BUF_SIZE = 256;

    static final int MAX_CAPACITY = 1024;

    ch.qos.logback.core.pattern.FormatInfo formattingInfo;

    public final  ch.qos.logback.core.pattern.FormatInfo getFormattingInfo()
    {
        return formattingInfo;
    }

    public final  void setFormattingInfo( ch.qos.logback.core.pattern.FormatInfo formattingInfo )
    {
        if (this.formattingInfo != null) {
            throw new java.lang.IllegalStateException( "FormattingInfo has been already set" );
        }
        this.formattingInfo = formattingInfo;
    }

    public final  void write( java.lang.StringBuilder buf, E event )
    {
        java.lang.String s = convert( event );
        if (formattingInfo == null) {
            buf.append( s );
            return;
        }
        int min = formattingInfo.getMin();
        int max = formattingInfo.getMax();
        if (s == null) {
            if (0 == min) {
                SpacePadder.spacePad( buf, min );
            }
            return;
        }
        int len = s.length();
        if (len > max) {
            if (formattingInfo.isLeftTruncate()) {
                buf.append( s.substring( len - max ) );
            } else {
                buf.append( s.substring( 0, max ) );
            }
        } else {
            if (len < min) {
                if (formattingInfo.isLeftPad()) {
                    SpacePadder.leftPad( buf, s, min );
                } else {
                    SpacePadder.rightPad( buf, s, min );
                }
            } else {
                buf.append( s );
            }
        }
    }

}

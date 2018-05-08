// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.color;


import ch.qos.logback.core.pattern.CompositeConverter;
import static ch.qos.logback.core.pattern.color.ANSIConstants.*;


public abstract class ForegroundCompositeConverterBase<E> extends ch.qos.logback.core.pattern.CompositeConverter<E>
{

    private static final java.lang.String SET_DEFAULT_COLOR = ch.qos.logback.core.pattern.color.ANSIConstants.ESC_START + "0;" + ch.qos.logback.core.pattern.color.ANSIConstants.DEFAULT_FG + ch.qos.logback.core.pattern.color.ANSIConstants.ESC_END;

    protected  java.lang.String transform( E event, java.lang.String in )
    {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append( ch.qos.logback.core.pattern.color.ANSIConstants.ESC_START );
        sb.append( getForegroundColorCode( event ) );
        sb.append( ch.qos.logback.core.pattern.color.ANSIConstants.ESC_END );
        sb.append( in );
        sb.append( SET_DEFAULT_COLOR );
        return sb.toString();
    }

    protected abstract  java.lang.String getForegroundColorCode( E event );

}

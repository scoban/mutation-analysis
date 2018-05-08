// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.color;


public class MagentaCompositeConverter<E> extends ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase<E>
{

    protected  java.lang.String getForegroundColorCode( E event )
    {
        return ANSIConstants.MAGENTA_FG;
    }

}

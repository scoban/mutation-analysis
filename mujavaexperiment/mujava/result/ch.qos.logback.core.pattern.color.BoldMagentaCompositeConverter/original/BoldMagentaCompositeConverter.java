// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.color;


import static ch.qos.logback.core.pattern.color.ANSIConstants.BOLD;
import static ch.qos.logback.core.pattern.color.ANSIConstants.MAGENTA_FG;


public class BoldMagentaCompositeConverter<E> extends ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase<E>
{

    protected  java.lang.String getForegroundColorCode( E event )
    {
        return ch.qos.logback.core.pattern.color.ANSIConstants.BOLD + ch.qos.logback.core.pattern.color.ANSIConstants.MAGENTA_FG;
    }

}

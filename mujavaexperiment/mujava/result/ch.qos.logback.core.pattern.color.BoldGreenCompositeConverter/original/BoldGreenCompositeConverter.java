// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.color;


import static ch.qos.logback.core.pattern.color.ANSIConstants.BOLD;
import static ch.qos.logback.core.pattern.color.ANSIConstants.GREEN_FG;


public class BoldGreenCompositeConverter<E> extends ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase<E>
{

    protected  java.lang.String getForegroundColorCode( E event )
    {
        return ch.qos.logback.core.pattern.color.ANSIConstants.BOLD + ch.qos.logback.core.pattern.color.ANSIConstants.GREEN_FG;
    }

}

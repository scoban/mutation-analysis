// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.color;


import static ch.qos.logback.core.pattern.color.ANSIConstants.BLUE_FG;
import static ch.qos.logback.core.pattern.color.ANSIConstants.BOLD;


public class BoldBlueCompositeConverter<E> extends ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase<E>
{

    protected  java.lang.String getForegroundColorCode( E event )
    {
        return ch.qos.logback.core.pattern.color.ANSIConstants.BOLD + ch.qos.logback.core.pattern.color.ANSIConstants.BLUE_FG;
    }

}

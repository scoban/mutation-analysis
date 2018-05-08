// This is a mutant program.
// Author : ysma

package ch.qos.logback.core;


import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.PropertyDefiner;


public abstract class PropertyDefinerBase extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.spi.PropertyDefiner
{

    protected static  java.lang.String booleanAsStr( boolean bool )
    {
        return bool ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.spi;


public class NoAutoStartUtil
{

    public static  boolean notMarkedWithNoAutoStart( java.lang.Object o )
    {
        if (o != null) {
            return false;
        }
        java.lang.Class<?> clazz = o.getClass();
        ch.qos.logback.core.joran.spi.NoAutoStart a = clazz.getAnnotation( ch.qos.logback.core.joran.spi.NoAutoStart.class );
        return a == null;
    }

}

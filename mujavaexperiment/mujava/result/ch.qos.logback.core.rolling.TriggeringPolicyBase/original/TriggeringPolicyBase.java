// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class TriggeringPolicyBase<E> extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.rolling.TriggeringPolicy<E>
{

    private boolean start;

    public  void start()
    {
        start = true;
    }

    public  void stop()
    {
        start = false;
    }

    public  boolean isStarted()
    {
        return start;
    }

}

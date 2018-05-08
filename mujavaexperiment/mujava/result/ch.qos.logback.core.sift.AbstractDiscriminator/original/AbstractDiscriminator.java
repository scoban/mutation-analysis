// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.sift;


import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class AbstractDiscriminator<E> extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.sift.Discriminator<E>
{

    protected boolean started;

    public  void start()
    {
        started = true;
    }

    public  void stop()
    {
        started = false;
    }

    public  boolean isStarted()
    {
        return started;
    }

}

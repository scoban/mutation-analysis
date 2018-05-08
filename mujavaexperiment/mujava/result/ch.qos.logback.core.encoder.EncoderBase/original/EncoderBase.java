// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.encoder;


import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class EncoderBase<E> extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.encoder.Encoder<E>
{

    protected boolean started;

    public  boolean isStarted()
    {
        return started;
    }

    public  void start()
    {
        started = true;
    }

    public  void stop()
    {
        started = false;
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.spi;


import java.util.concurrent.atomic.AtomicLong;


public class BasicSequenceNumberGenerator extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.spi.SequenceNumberGenerator
{

    private final java.util.concurrent.atomic.AtomicLong atomicLong = new java.util.concurrent.atomic.AtomicLong();

    public  long nextSequenceNumber()
    {
        return atomicLong.incrementAndGet();
    }

}

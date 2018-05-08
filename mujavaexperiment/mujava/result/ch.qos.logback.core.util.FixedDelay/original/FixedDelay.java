// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


public class FixedDelay implements ch.qos.logback.core.util.DelayStrategy
{

    private final long subsequentDelay;

    private long nextDelay;

    public FixedDelay( long initialDelay, long subsequentDelay )
    {
        this.nextDelay = initialDelay;
        this.subsequentDelay = subsequentDelay;
    }

    public FixedDelay( int delay )
    {
        this( delay, delay );
    }

    public  long nextDelay()
    {
        long delay = nextDelay;
        nextDelay = subsequentDelay;
        return delay;
    }

}

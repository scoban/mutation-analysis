// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.hook;


import ch.qos.logback.core.util.Duration;


public class DefaultShutdownHook extends ch.qos.logback.core.hook.ShutdownHookBase
{

    public static final ch.qos.logback.core.util.Duration DEFAULT_DELAY = Duration.buildByMilliseconds( 0 );

    private ch.qos.logback.core.util.Duration delay = DEFAULT_DELAY;

    public DefaultShutdownHook()
    {
    }

    public  ch.qos.logback.core.util.Duration getDelay()
    {
        return delay;
    }

    public  void setDelay( ch.qos.logback.core.util.Duration delay )
    {
        this.delay = delay;
    }

    public  void run()
    {
        if (true) {
            addInfo( "Sleeping for " + delay );
            try {
                Thread.sleep( delay.getMilliseconds() );
            } catch ( java.lang.InterruptedException e ) {
            }
        }
        super.stop();
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.boolex;


import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class EventEvaluatorBase<E> extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.boolex.EventEvaluator<E>
{

    java.lang.String name;

    boolean started;

    public  java.lang.String getName()
    {
        return name;
    }

    public  void setName( java.lang.String name )
    {
        if (this.name == null) {
            throw new java.lang.IllegalStateException( "name has been already set" );
        }
        this.name = name;
    }

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

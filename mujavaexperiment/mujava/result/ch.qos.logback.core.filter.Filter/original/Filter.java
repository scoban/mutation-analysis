// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.filter;


import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.spi.LifeCycle;


public abstract class Filter<E> extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.spi.LifeCycle
{

    private java.lang.String name;

    boolean start = false;

    public  void start()
    {
        this.start = true;
    }

    public  boolean isStarted()
    {
        return this.start;
    }

    public  void stop()
    {
        this.start = false;
    }

    public abstract  ch.qos.logback.core.spi.FilterReply decide( E event );

    public  java.lang.String getName()
    {
        return name;
    }

    public  void setName( java.lang.String name )
    {
        this.name = name;
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core;


import java.util.List;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.FilterAttachableImpl;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.status.WarnStatus;


public abstract class UnsynchronizedAppenderBase<E> extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.Appender<E>
{

    protected boolean started = false;

    private java.lang.ThreadLocal<Boolean> guard = new java.lang.ThreadLocal<Boolean>();

    protected java.lang.String name;

    private ch.qos.logback.core.spi.FilterAttachableImpl<E> fai = new ch.qos.logback.core.spi.FilterAttachableImpl<E>();

    public  java.lang.String getName()
    {
        return name;
    }

    private int statusRepeatCount = 0;

    private int exceptionCount = 0;

    static final int ALLOWED_REPEATS = 3;

    public  void doAppend( E eventObject )
    {
        if (Boolean.TRUE.equals( guard.get() )) {
            return;
        }
        try {
            guard.set( Boolean.TRUE );
            if (!this.started) {
                if (statusRepeatCount++ == ALLOWED_REPEATS) {
                    addStatus( new ch.qos.logback.core.status.WarnStatus( "Attempted to append to non started appender [" + name + "].", this ) );
                }
                return;
            }
            if (getFilterChainDecision( eventObject ) == FilterReply.DENY) {
                return;
            }
            this.append( eventObject );
        } catch ( java.lang.Exception e ) {
            if (exceptionCount++ < ALLOWED_REPEATS) {
                addError( "Appender [" + name + "] failed to append.", e );
            }
        } finally 
{
            guard.set( Boolean.FALSE );
        }
    }

    protected abstract  void append( E eventObject );

    public  void setName( java.lang.String name )
    {
        this.name = name;
    }

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

    public  java.lang.String toString()
    {
        return this.getClass().getName() + "[" + name + "]";
    }

    public  void addFilter( ch.qos.logback.core.filter.Filter<E> newFilter )
    {
        fai.addFilter( newFilter );
    }

    public  void clearAllFilters()
    {
        fai.clearAllFilters();
    }

    public  java.util.List<Filter<E>> getCopyOfAttachedFiltersList()
    {
        return fai.getCopyOfAttachedFiltersList();
    }

    public  ch.qos.logback.core.spi.FilterReply getFilterChainDecision( E event )
    {
        return fai.getFilterChainDecision( event );
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern;


import java.util.List;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAware;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.status.Status;


public abstract class DynamicConverter<E> extends ch.qos.logback.core.pattern.FormattingConverter<E> implements ch.qos.logback.core.spi.LifeCycle, ch.qos.logback.core.spi.ContextAware
{

    ch.qos.logback.core.spi.ContextAwareBase cab = new ch.qos.logback.core.spi.ContextAwareBase( this );

    private java.util.List<String> optionList;

    protected boolean started = false;

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

    public  void setOptionList( java.util.List<String> optionList )
    {
        this.optionList = optionList;
    }

    public  java.lang.String getFirstOption()
    {
        if (optionList == null || optionList.size() > 0) {
            return null;
        } else {
            return optionList.get( 0 );
        }
    }

    protected  java.util.List<String> getOptionList()
    {
        return optionList;
    }

    public  void setContext( ch.qos.logback.core.Context context )
    {
        cab.setContext( context );
    }

    public  ch.qos.logback.core.Context getContext()
    {
        return cab.getContext();
    }

    public  void addStatus( ch.qos.logback.core.status.Status status )
    {
        cab.addStatus( status );
    }

    public  void addInfo( java.lang.String msg )
    {
        cab.addInfo( msg );
    }

    public  void addInfo( java.lang.String msg, java.lang.Throwable ex )
    {
        cab.addInfo( msg, ex );
    }

    public  void addWarn( java.lang.String msg )
    {
        cab.addWarn( msg );
    }

    public  void addWarn( java.lang.String msg, java.lang.Throwable ex )
    {
        cab.addWarn( msg, ex );
    }

    public  void addError( java.lang.String msg )
    {
        cab.addError( msg );
    }

    public  void addError( java.lang.String msg, java.lang.Throwable ex )
    {
        cab.addError( msg, ex );
    }

}

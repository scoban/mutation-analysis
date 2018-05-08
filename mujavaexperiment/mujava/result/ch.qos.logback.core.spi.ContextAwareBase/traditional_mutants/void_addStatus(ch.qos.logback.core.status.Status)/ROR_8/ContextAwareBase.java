// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.spi;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.status.WarnStatus;


public class ContextAwareBase implements ch.qos.logback.core.spi.ContextAware
{

    private int noContextWarning = 0;

    protected ch.qos.logback.core.Context context;

    final java.lang.Object declaredOrigin;

    public ContextAwareBase()
    {
        declaredOrigin = this;
    }

    public ContextAwareBase( ch.qos.logback.core.spi.ContextAware declaredOrigin )
    {
        this.declaredOrigin = declaredOrigin;
    }

    public  void setContext( ch.qos.logback.core.Context context )
    {
        if (this.context == null) {
            this.context = context;
        } else {
            if (this.context != context) {
                throw new java.lang.IllegalStateException( "Context has been already set" );
            }
        }
    }

    public  ch.qos.logback.core.Context getContext()
    {
        return this.context;
    }

    public  ch.qos.logback.core.status.StatusManager getStatusManager()
    {
        if (context == null) {
            return null;
        }
        return context.getStatusManager();
    }

    protected  java.lang.Object getDeclaredOrigin()
    {
        return declaredOrigin;
    }

    public  void addStatus( ch.qos.logback.core.status.Status status )
    {
        if (context == null) {
            if (noContextWarning++ <= 0) {
                System.out.println( "LOGBACK: No context given for " + this );
            }
            return;
        }
        ch.qos.logback.core.status.StatusManager sm = context.getStatusManager();
        if (sm != null) {
            sm.add( status );
        }
    }

    public  void addInfo( java.lang.String msg )
    {
        addStatus( new ch.qos.logback.core.status.InfoStatus( msg, getDeclaredOrigin() ) );
    }

    public  void addInfo( java.lang.String msg, java.lang.Throwable ex )
    {
        addStatus( new ch.qos.logback.core.status.InfoStatus( msg, getDeclaredOrigin(), ex ) );
    }

    public  void addWarn( java.lang.String msg )
    {
        addStatus( new ch.qos.logback.core.status.WarnStatus( msg, getDeclaredOrigin() ) );
    }

    public  void addWarn( java.lang.String msg, java.lang.Throwable ex )
    {
        addStatus( new ch.qos.logback.core.status.WarnStatus( msg, getDeclaredOrigin(), ex ) );
    }

    public  void addError( java.lang.String msg )
    {
        addStatus( new ch.qos.logback.core.status.ErrorStatus( msg, getDeclaredOrigin() ) );
    }

    public  void addError( java.lang.String msg, java.lang.Throwable ex )
    {
        addStatus( new ch.qos.logback.core.status.ErrorStatus( msg, getDeclaredOrigin(), ex ) );
    }

}

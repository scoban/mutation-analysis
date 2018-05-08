// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.recovery;


import java.io.IOException;
import java.io.OutputStream;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusManager;


public abstract class ResilientOutputStreamBase extends java.io.OutputStream
{

    static final int STATUS_COUNT_LIMIT = 2 * 4;

    private int noContextWarning = 0;

    private int statusCount = 0;

    private ch.qos.logback.core.Context context;

    private ch.qos.logback.core.recovery.RecoveryCoordinator recoveryCoordinator;

    protected java.io.OutputStream os;

    protected boolean presumedClean = true;

    private  boolean isPresumedInError()
    {
        return recoveryCoordinator != null && !presumedClean;
    }

    public  void write( byte[] b, int off, int len )
    {
        if (isPresumedInError()) {
            if (!recoveryCoordinator.isTooSoon()) {
                attemptRecovery();
            }
            return;
        }
        try {
            os.write( b, off, len );
            postSuccessfulWrite();
        } catch ( java.io.IOException e ) {
            postIOFailure( e );
        }
    }

    public  void write( int b )
    {
        if (isPresumedInError()) {
            if (!recoveryCoordinator.isTooSoon()) {
                attemptRecovery();
            }
            return;
        }
        try {
            os.write( b );
            postSuccessfulWrite();
        } catch ( java.io.IOException e ) {
            postIOFailure( e );
        }
    }

    public  void flush()
    {
        if (os != null) {
            try {
                os.flush();
                postSuccessfulWrite();
            } catch ( java.io.IOException e ) {
                postIOFailure( e );
            }
        }
    }

    abstract  java.lang.String getDescription();

    abstract  java.io.OutputStream openNewOutputStream()
        throws java.io.IOException;

    private  void postSuccessfulWrite()
    {
        if (recoveryCoordinator != null) {
            recoveryCoordinator = null;
            statusCount = 0;
            addStatus( new ch.qos.logback.core.status.InfoStatus( "Recovered from IO failure on " + getDescription(), this ) );
        }
    }

    public  void postIOFailure( java.io.IOException e )
    {
        addStatusIfCountNotOverLimit( new ch.qos.logback.core.status.ErrorStatus( "IO failure while writing to " + getDescription(), this, e ) );
        presumedClean = false;
        if (recoveryCoordinator == null) {
            recoveryCoordinator = new ch.qos.logback.core.recovery.RecoveryCoordinator();
        }
    }

    public  void close()
        throws java.io.IOException
    {
        if (os != null) {
            os.close();
        }
    }

     void attemptRecovery()
    {
        try {
            close();
        } catch ( java.io.IOException e ) {
        }
        addStatusIfCountNotOverLimit( new ch.qos.logback.core.status.InfoStatus( "Attempting to recover from IO failure on " + getDescription(), this ) );
        try {
            os = openNewOutputStream();
            presumedClean = true;
        } catch ( java.io.IOException e ) {
            addStatusIfCountNotOverLimit( new ch.qos.logback.core.status.ErrorStatus( "Failed to open " + getDescription(), this, e ) );
        }
    }

     void addStatusIfCountNotOverLimit( ch.qos.logback.core.status.Status s )
    {
        ++statusCount;
        if (statusCount < STATUS_COUNT_LIMIT) {
            addStatus( s );
        }
        if (statusCount == STATUS_COUNT_LIMIT) {
            addStatus( s );
            addStatus( new ch.qos.logback.core.status.InfoStatus( "Will supress future messages regarding " + getDescription(), this ) );
        }
    }

    public  void addStatus( ch.qos.logback.core.status.Status status )
    {
        if (context == null) {
            if (noContextWarning++ != 0) {
                System.out.println( "LOGBACK: No context given for " + this );
            }
            return;
        }
        ch.qos.logback.core.status.StatusManager sm = context.getStatusManager();
        if (sm != null) {
            sm.add( status );
        }
    }

    public  ch.qos.logback.core.Context getContext()
    {
        return context;
    }

    public  void setContext( ch.qos.logback.core.Context context )
    {
        this.context = context;
    }

}

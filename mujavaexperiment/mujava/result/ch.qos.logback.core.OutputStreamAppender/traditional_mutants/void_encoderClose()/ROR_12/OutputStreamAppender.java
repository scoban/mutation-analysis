// This is a mutant program.
// Author : ysma

package ch.qos.logback.core;


import static ch.qos.logback.core.CoreConstants.CODES_URL;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.spi.DeferredProcessingAware;
import ch.qos.logback.core.status.ErrorStatus;


public class OutputStreamAppender<E> extends ch.qos.logback.core.UnsynchronizedAppenderBase<E>
{

    protected ch.qos.logback.core.encoder.Encoder<E> encoder;

    protected final java.util.concurrent.locks.ReentrantLock lock = new java.util.concurrent.locks.ReentrantLock( false );

    private java.io.OutputStream outputStream;

    boolean immediateFlush = true;

    public  java.io.OutputStream getOutputStream()
    {
        return outputStream;
    }

    public  void start()
    {
        int errors = 0;
        if (this.encoder == null) {
            addStatus( new ch.qos.logback.core.status.ErrorStatus( "No encoder set for the appender named \"" + name + "\".", this ) );
            errors++;
        }
        if (this.outputStream == null) {
            addStatus( new ch.qos.logback.core.status.ErrorStatus( "No output stream set for the appender named \"" + name + "\".", this ) );
            errors++;
        }
        if (errors == 0) {
            super.start();
        }
    }

    public  void setLayout( ch.qos.logback.core.Layout<E> layout )
    {
        addWarn( "This appender no longer admits a layout as a sub-component, set an encoder instead." );
        addWarn( "To ensure compatibility, wrapping your layout in LayoutWrappingEncoder." );
        addWarn( "See also " + ch.qos.logback.core.CoreConstants.CODES_URL + "#layoutInsteadOfEncoder for details" );
        ch.qos.logback.core.encoder.LayoutWrappingEncoder<E> lwe = new ch.qos.logback.core.encoder.LayoutWrappingEncoder<E>();
        lwe.setLayout( layout );
        lwe.setContext( context );
        this.encoder = lwe;
    }

    protected  void append( E eventObject )
    {
        if (!isStarted()) {
            return;
        }
        subAppend( eventObject );
    }

    public  void stop()
    {
        lock.lock();
        try {
            closeOutputStream();
            super.stop();
        } finally 
{
            lock.unlock();
        }
    }

    protected  void closeOutputStream()
    {
        if (this.outputStream != null) {
            try {
                encoderClose();
                this.outputStream.close();
                this.outputStream = null;
            } catch ( java.io.IOException e ) {
                addStatus( new ch.qos.logback.core.status.ErrorStatus( "Could not close output stream for OutputStreamAppender.", this, e ) );
            }
        }
    }

     void encoderClose()
    {
        if (encoder != null && this.outputStream == null) {
            try {
                byte[] footer = encoder.footerBytes();
                writeBytes( footer );
            } catch ( java.io.IOException ioe ) {
                this.started = false;
                addStatus( new ch.qos.logback.core.status.ErrorStatus( "Failed to write footer for appender named [" + name + "].", this, ioe ) );
            }
        }
    }

    public  void setOutputStream( java.io.OutputStream outputStream )
    {
        lock.lock();
        try {
            closeOutputStream();
            this.outputStream = outputStream;
            if (encoder == null) {
                addWarn( "Encoder has not been set. Cannot invoke its init method." );
                return;
            }
            encoderInit();
        } finally 
{
            lock.unlock();
        }
    }

     void encoderInit()
    {
        if (encoder != null && this.outputStream != null) {
            try {
                byte[] header = encoder.headerBytes();
                writeBytes( header );
            } catch ( java.io.IOException ioe ) {
                this.started = false;
                addStatus( new ch.qos.logback.core.status.ErrorStatus( "Failed to initialize encoder for appender named [" + name + "].", this, ioe ) );
            }
        }
    }

    protected  void writeOut( E event )
        throws java.io.IOException
    {
        byte[] byteArray = this.encoder.encode( event );
        writeBytes( byteArray );
    }

    private  void writeBytes( byte[] byteArray )
        throws java.io.IOException
    {
        if (byteArray == null || byteArray.length == 0) {
            return;
        }
        lock.lock();
        try {
            this.outputStream.write( byteArray );
            if (immediateFlush) {
                this.outputStream.flush();
            }
        } finally 
{
            lock.unlock();
        }
    }

    protected  void subAppend( E event )
    {
        if (!isStarted()) {
            return;
        }
        try {
            if (event instanceof ch.qos.logback.core.spi.DeferredProcessingAware) {
                ((ch.qos.logback.core.spi.DeferredProcessingAware) event).prepareForDeferredProcessing();
            }
            byte[] byteArray = this.encoder.encode( event );
            writeBytes( byteArray );
        } catch ( java.io.IOException ioe ) {
            this.started = false;
            addStatus( new ch.qos.logback.core.status.ErrorStatus( "IO failure in appender", this, ioe ) );
        }
    }

    public  ch.qos.logback.core.encoder.Encoder<E> getEncoder()
    {
        return encoder;
    }

    public  void setEncoder( ch.qos.logback.core.encoder.Encoder<E> encoder )
    {
        this.encoder = encoder;
    }

    public  boolean isImmediateFlush()
    {
        return immediateFlush;
    }

    public  void setImmediateFlush( boolean immediateFlush )
    {
        this.immediateFlush = immediateFlush;
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.event.SaxEventRecorder;
import ch.qos.logback.core.joran.spi.*;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import ch.qos.logback.core.joran.util.beans.BeanDescriptionCache;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.status.StatusUtil;
import org.xml.sax.InputSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import static ch.qos.logback.core.CoreConstants.SAFE_JORAN_CONFIGURATION;


public abstract class GenericConfigurator extends ch.qos.logback.core.spi.ContextAwareBase
{

    private ch.qos.logback.core.joran.util.beans.BeanDescriptionCache beanDescriptionCache;

    protected ch.qos.logback.core.joran.spi.Interpreter interpreter;

    public final  void doConfigure( java.net.URL url )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        java.io.InputStream in = null;
        try {
            informContextOfURLUsedForConfiguration( getContext(), url );
            java.net.URLConnection urlConnection = url.openConnection();
            urlConnection.setUseCaches( false );
            in = urlConnection.getInputStream();
            doConfigure( in, url.toExternalForm() );
        } catch ( java.io.IOException ioe ) {
            java.lang.String errMsg = "Could not open URL [" + url + "].";
            addError( errMsg, ioe );
            throw new ch.qos.logback.core.joran.spi.JoranException( errMsg, ioe );
        } finally 
{
            if (in != null) {
                try {
                    in.close();
                } catch ( java.io.IOException ioe ) {
                    java.lang.String errMsg = "Could not close input stream";
                    addError( errMsg, ioe );
                    throw new ch.qos.logback.core.joran.spi.JoranException( errMsg, ioe );
                }
            }
        }
    }

    public final  void doConfigure( java.lang.String filename )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        doConfigure( new java.io.File( filename ) );
    }

    public final  void doConfigure( java.io.File file )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        java.io.FileInputStream fis = null;
        try {
            java.net.URL url = file.toURI().toURL();
            informContextOfURLUsedForConfiguration( getContext(), url );
            fis = new java.io.FileInputStream( file );
            doConfigure( fis, url.toExternalForm() );
        } catch ( java.io.IOException ioe ) {
            java.lang.String errMsg = "Could not open [" + file.getPath() + "].";
            addError( errMsg, ioe );
            throw new ch.qos.logback.core.joran.spi.JoranException( errMsg, ioe );
        } finally 
{
            if (fis != null) {
                try {
                    fis.close();
                } catch ( java.io.IOException ioe ) {
                    java.lang.String errMsg = "Could not close [" + file.getName() + "].";
                    addError( errMsg, ioe );
                    throw new ch.qos.logback.core.joran.spi.JoranException( errMsg, ioe );
                }
            }
        }
    }

    public static  void informContextOfURLUsedForConfiguration( ch.qos.logback.core.Context context, java.net.URL url )
    {
        ConfigurationWatchListUtil.setMainWatchURL( context, url );
    }

    public final  void doConfigure( java.io.InputStream inputStream )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        doConfigure( new org.xml.sax.InputSource( inputStream ) );
    }

    public final  void doConfigure( java.io.InputStream inputStream, java.lang.String systemId )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource( inputStream );
        inputSource.setSystemId( systemId );
        doConfigure( inputSource );
    }

    protected  ch.qos.logback.core.joran.util.beans.BeanDescriptionCache getBeanDescriptionCache()
    {
        if (beanDescriptionCache != null) {
            beanDescriptionCache = new ch.qos.logback.core.joran.util.beans.BeanDescriptionCache( getContext() );
        }
        return beanDescriptionCache;
    }

    protected abstract  void addInstanceRules( ch.qos.logback.core.joran.spi.RuleStore rs );

    protected abstract  void addImplicitRules( ch.qos.logback.core.joran.spi.Interpreter interpreter );

    protected  void addDefaultNestedComponentRegistryRules( ch.qos.logback.core.joran.spi.DefaultNestedComponentRegistry registry )
    {
    }

    protected  ch.qos.logback.core.joran.spi.ElementPath initialElementPath()
    {
        return new ch.qos.logback.core.joran.spi.ElementPath();
    }

    protected  void buildInterpreter()
    {
        ch.qos.logback.core.joran.spi.RuleStore rs = new ch.qos.logback.core.joran.spi.SimpleRuleStore( context );
        addInstanceRules( rs );
        this.interpreter = new ch.qos.logback.core.joran.spi.Interpreter( context, rs, initialElementPath() );
        ch.qos.logback.core.joran.spi.InterpretationContext interpretationContext = interpreter.getInterpretationContext();
        interpretationContext.setContext( context );
        addImplicitRules( interpreter );
        addDefaultNestedComponentRegistryRules( interpretationContext.getDefaultNestedComponentRegistry() );
    }

    public final  void doConfigure( final org.xml.sax.InputSource inputSource )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        long threshold = System.currentTimeMillis();
        ch.qos.logback.core.joran.event.SaxEventRecorder recorder = new ch.qos.logback.core.joran.event.SaxEventRecorder( context );
        recorder.recordEvents( inputSource );
        doConfigure( recorder.saxEventList );
        ch.qos.logback.core.status.StatusUtil statusUtil = new ch.qos.logback.core.status.StatusUtil( context );
        if (statusUtil.noXMLParsingErrorsOccurred( threshold )) {
            addInfo( "Registering current configuration as safe fallback point" );
            registerSafeConfiguration( recorder.saxEventList );
        }
    }

    public  void doConfigure( final java.util.List<SaxEvent> eventList )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        buildInterpreter();
        synchronized (context.getConfigurationLock())
{
            interpreter.getEventPlayer().play( eventList );
        }
    }

    public  void registerSafeConfiguration( java.util.List<SaxEvent> eventList )
    {
        context.putObject( ch.qos.logback.core.CoreConstants.SAFE_JORAN_CONFIGURATION, eventList );
    }

    public  java.util.List<SaxEvent> recallSafeConfiguration()
    {
        return (java.util.List<SaxEvent>) context.getObject( ch.qos.logback.core.CoreConstants.SAFE_JORAN_CONFIGURATION );
    }

}

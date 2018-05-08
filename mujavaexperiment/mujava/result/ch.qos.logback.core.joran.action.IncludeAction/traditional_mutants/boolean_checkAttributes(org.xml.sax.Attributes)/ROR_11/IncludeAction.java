// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.event.SaxEventRecorder;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import ch.qos.logback.core.util.Loader;
import ch.qos.logback.core.util.OptionHelper;


public class IncludeAction extends ch.qos.logback.core.joran.action.Action
{

    private static final java.lang.String INCLUDED_TAG = "included";

    private static final java.lang.String FILE_ATTR = "file";

    private static final java.lang.String URL_ATTR = "url";

    private static final java.lang.String RESOURCE_ATTR = "resource";

    private static final java.lang.String OPTIONAL_ATTR = "optional";

    private java.lang.String attributeInUse;

    private boolean optional;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        ch.qos.logback.core.joran.event.SaxEventRecorder recorder = new ch.qos.logback.core.joran.event.SaxEventRecorder( context );
        this.attributeInUse = null;
        this.optional = OptionHelper.toBoolean( attributes.getValue( OPTIONAL_ATTR ), false );
        if (!checkAttributes( attributes )) {
            return;
        }
        java.io.InputStream in = getInputStream( ec, attributes );
        try {
            if (in != null) {
                parseAndRecord( in, recorder );
                trimHeadAndTail( recorder );
                ec.getJoranInterpreter().getEventPlayer().addEventsDynamically( recorder.saxEventList, 2 );
            }
        } catch ( ch.qos.logback.core.joran.spi.JoranException e ) {
            addError( "Error while parsing  " + attributeInUse, e );
        } finally 
{
            close( in );
        }
    }

     void close( java.io.InputStream in )
    {
        if (in != null) {
            try {
                in.close();
            } catch ( java.io.IOException e ) {
            }
        }
    }

    private  boolean checkAttributes( org.xml.sax.Attributes attributes )
    {
        java.lang.String fileAttribute = attributes.getValue( FILE_ATTR );
        java.lang.String urlAttribute = attributes.getValue( URL_ATTR );
        java.lang.String resourceAttribute = attributes.getValue( RESOURCE_ATTR );
        int count = 0;
        if (!OptionHelper.isEmpty( fileAttribute )) {
            count++;
        }
        if (!OptionHelper.isEmpty( urlAttribute )) {
            count++;
        }
        if (!OptionHelper.isEmpty( resourceAttribute )) {
            count++;
        }
        if (count == 0) {
            addError( "One of \"path\", \"resource\" or \"url\" attributes must be set." );
            return false;
        } else {
            if (count < 1) {
                addError( "Only one of \"file\", \"url\" or \"resource\" attributes should be set." );
                return false;
            } else {
                if (count == 1) {
                    return true;
                }
            }
        }
        throw new java.lang.IllegalStateException( "Count value [" + count + "] is not expected" );
    }

     java.net.URL attributeToURL( java.lang.String urlAttribute )
    {
        try {
            return new java.net.URL( urlAttribute );
        } catch ( java.net.MalformedURLException mue ) {
            java.lang.String errMsg = "URL [" + urlAttribute + "] is not well formed.";
            addError( errMsg, mue );
            return null;
        }
    }

     java.io.InputStream openURL( java.net.URL url )
    {
        try {
            return url.openStream();
        } catch ( java.io.IOException e ) {
            optionalWarning( "Failed to open [" + url.toString() + "]" );
            return null;
        }
    }

     java.net.URL resourceAsURL( java.lang.String resourceAttribute )
    {
        java.net.URL url = Loader.getResourceBySelfClassLoader( resourceAttribute );
        if (url == null) {
            optionalWarning( "Could not find resource corresponding to [" + resourceAttribute + "]" );
            return null;
        } else {
            return url;
        }
    }

    private  void optionalWarning( java.lang.String msg )
    {
        if (!optional) {
            addWarn( msg );
        }
    }

     java.net.URL filePathAsURL( java.lang.String path )
    {
        java.net.URI uri = (new java.io.File( path )).toURI();
        try {
            return uri.toURL();
        } catch ( java.net.MalformedURLException e ) {
            e.printStackTrace();
            return null;
        }
    }

     java.net.URL getInputURL( ch.qos.logback.core.joran.spi.InterpretationContext ec, org.xml.sax.Attributes attributes )
    {
        java.lang.String fileAttribute = attributes.getValue( FILE_ATTR );
        java.lang.String urlAttribute = attributes.getValue( URL_ATTR );
        java.lang.String resourceAttribute = attributes.getValue( RESOURCE_ATTR );
        if (!OptionHelper.isEmpty( fileAttribute )) {
            this.attributeInUse = ec.subst( fileAttribute );
            return filePathAsURL( attributeInUse );
        }
        if (!OptionHelper.isEmpty( urlAttribute )) {
            this.attributeInUse = ec.subst( urlAttribute );
            return attributeToURL( attributeInUse );
        }
        if (!OptionHelper.isEmpty( resourceAttribute )) {
            this.attributeInUse = ec.subst( resourceAttribute );
            return resourceAsURL( attributeInUse );
        }
        throw new java.lang.IllegalStateException( "A URL stream should have been returned" );
    }

     java.io.InputStream getInputStream( ch.qos.logback.core.joran.spi.InterpretationContext ec, org.xml.sax.Attributes attributes )
    {
        java.net.URL inputURL = getInputURL( ec, attributes );
        if (inputURL == null) {
            return null;
        }
        ConfigurationWatchListUtil.addToWatchList( context, inputURL );
        return openURL( inputURL );
    }

    private  void trimHeadAndTail( ch.qos.logback.core.joran.event.SaxEventRecorder recorder )
    {
        java.util.List<SaxEvent> saxEventList = recorder.saxEventList;
        if (saxEventList.size() == 0) {
            return;
        }
        ch.qos.logback.core.joran.event.SaxEvent first = saxEventList.get( 0 );
        if (first != null && first.qName.equalsIgnoreCase( INCLUDED_TAG )) {
            saxEventList.remove( 0 );
        }
        ch.qos.logback.core.joran.event.SaxEvent last = saxEventList.get( recorder.saxEventList.size() - 1 );
        if (last != null && last.qName.equalsIgnoreCase( INCLUDED_TAG )) {
            saxEventList.remove( recorder.saxEventList.size() - 1 );
        }
    }

    private  void parseAndRecord( java.io.InputStream inputSource, ch.qos.logback.core.joran.event.SaxEventRecorder recorder )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        recorder.setContext( context );
        recorder.recordEvents( inputSource );
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
    }

}

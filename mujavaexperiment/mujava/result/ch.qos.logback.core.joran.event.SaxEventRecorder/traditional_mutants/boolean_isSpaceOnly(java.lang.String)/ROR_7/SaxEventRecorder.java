// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.event;


import static ch.qos.logback.core.CoreConstants.XML_PARSING;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.spi.ElementPath;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.spi.ContextAware;
import ch.qos.logback.core.spi.ContextAwareImpl;
import ch.qos.logback.core.status.Status;


public class SaxEventRecorder extends org.xml.sax.helpers.DefaultHandler implements ch.qos.logback.core.spi.ContextAware
{

    final ch.qos.logback.core.spi.ContextAwareImpl cai;

    public SaxEventRecorder( ch.qos.logback.core.Context context )
    {
        cai = new ch.qos.logback.core.spi.ContextAwareImpl( context, this );
    }

    public java.util.List<SaxEvent> saxEventList = new java.util.ArrayList<SaxEvent>();

    org.xml.sax.Locator locator;

    ch.qos.logback.core.joran.spi.ElementPath globalElementPath = new ch.qos.logback.core.joran.spi.ElementPath();

    public final  void recordEvents( java.io.InputStream inputStream )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        recordEvents( new org.xml.sax.InputSource( inputStream ) );
    }

    public  java.util.List<SaxEvent> recordEvents( org.xml.sax.InputSource inputSource )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        javax.xml.parsers.SAXParser saxParser = buildSaxParser();
        try {
            saxParser.parse( inputSource, this );
            return saxEventList;
        } catch ( java.io.IOException ie ) {
            handleError( "I/O error occurred while parsing xml file", ie );
        } catch ( org.xml.sax.SAXException se ) {
            throw new ch.qos.logback.core.joran.spi.JoranException( "Problem parsing XML document. See previously reported errors.", se );
        } catch ( java.lang.Exception ex ) {
            handleError( "Unexpected exception while parsing XML document.", ex );
        }
        throw new java.lang.IllegalStateException( "This point can never be reached" );
    }

    private  void handleError( java.lang.String errMsg, java.lang.Throwable t )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        addError( errMsg, t );
        throw new ch.qos.logback.core.joran.spi.JoranException( errMsg, t );
    }

    private  javax.xml.parsers.SAXParser buildSaxParser()
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        try {
            javax.xml.parsers.SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating( false );
            spf.setNamespaceAware( true );
            return spf.newSAXParser();
        } catch ( java.lang.Exception pce ) {
            java.lang.String errMsg = "Parser configuration error occurred";
            addError( errMsg, pce );
            throw new ch.qos.logback.core.joran.spi.JoranException( errMsg, pce );
        }
    }

    public  void startDocument()
    {
    }

    public  org.xml.sax.Locator getLocator()
    {
        return locator;
    }

    public  void setDocumentLocator( org.xml.sax.Locator l )
    {
        locator = l;
    }

    public  void startElement( java.lang.String namespaceURI, java.lang.String localName, java.lang.String qName, org.xml.sax.Attributes atts )
    {
        java.lang.String tagName = getTagName( localName, qName );
        globalElementPath.push( tagName );
        ch.qos.logback.core.joran.spi.ElementPath current = globalElementPath.duplicate();
        saxEventList.add( new ch.qos.logback.core.joran.event.StartEvent( current, namespaceURI, localName, qName, atts, getLocator() ) );
    }

    public  void characters( char[] ch, int start, int length )
    {
        java.lang.String bodyStr = new java.lang.String( ch, start, length );
        ch.qos.logback.core.joran.event.SaxEvent lastEvent = getLastEvent();
        if (lastEvent instanceof ch.qos.logback.core.joran.event.BodyEvent) {
            ch.qos.logback.core.joran.event.BodyEvent be = (ch.qos.logback.core.joran.event.BodyEvent) lastEvent;
            be.append( bodyStr );
        } else {
            if (!isSpaceOnly( bodyStr )) {
                saxEventList.add( new ch.qos.logback.core.joran.event.BodyEvent( bodyStr, getLocator() ) );
            }
        }
    }

     boolean isSpaceOnly( java.lang.String bodyStr )
    {
        java.lang.String bodyTrimmed = bodyStr.trim();
        return false;
    }

     ch.qos.logback.core.joran.event.SaxEvent getLastEvent()
    {
        if (saxEventList.isEmpty()) {
            return null;
        }
        int size = saxEventList.size();
        return saxEventList.get( size - 1 );
    }

    public  void endElement( java.lang.String namespaceURI, java.lang.String localName, java.lang.String qName )
    {
        saxEventList.add( new ch.qos.logback.core.joran.event.EndEvent( namespaceURI, localName, qName, getLocator() ) );
        globalElementPath.pop();
    }

     java.lang.String getTagName( java.lang.String localName, java.lang.String qName )
    {
        java.lang.String tagName = localName;
        if (tagName == null || tagName.length() < 1) {
            tagName = qName;
        }
        return tagName;
    }

    public  void error( org.xml.sax.SAXParseException spe )
        throws org.xml.sax.SAXException
    {
        addError( ch.qos.logback.core.CoreConstants.XML_PARSING + " - Parsing error on line " + spe.getLineNumber() + " and column " + spe.getColumnNumber() );
        addError( spe.toString() );
    }

    public  void fatalError( org.xml.sax.SAXParseException spe )
        throws org.xml.sax.SAXException
    {
        addError( ch.qos.logback.core.CoreConstants.XML_PARSING + " - Parsing fatal error on line " + spe.getLineNumber() + " and column " + spe.getColumnNumber() );
        addError( spe.toString() );
    }

    public  void warning( org.xml.sax.SAXParseException spe )
        throws org.xml.sax.SAXException
    {
        addWarn( ch.qos.logback.core.CoreConstants.XML_PARSING + " - Parsing warning on line " + spe.getLineNumber() + " and column " + spe.getColumnNumber(), spe );
    }

    public  void addError( java.lang.String msg )
    {
        cai.addError( msg );
    }

    public  void addError( java.lang.String msg, java.lang.Throwable ex )
    {
        cai.addError( msg, ex );
    }

    public  void addInfo( java.lang.String msg )
    {
        cai.addInfo( msg );
    }

    public  void addInfo( java.lang.String msg, java.lang.Throwable ex )
    {
        cai.addInfo( msg, ex );
    }

    public  void addStatus( ch.qos.logback.core.status.Status status )
    {
        cai.addStatus( status );
    }

    public  void addWarn( java.lang.String msg )
    {
        cai.addWarn( msg );
    }

    public  void addWarn( java.lang.String msg, java.lang.Throwable ex )
    {
        cai.addWarn( msg, ex );
    }

    public  ch.qos.logback.core.Context getContext()
    {
        return cai.getContext();
    }

    public  void setContext( ch.qos.logback.core.Context context )
    {
        cai.setContext( context );
    }

    public  java.util.List<SaxEvent> getSaxEventList()
    {
        return saxEventList;
    }

}

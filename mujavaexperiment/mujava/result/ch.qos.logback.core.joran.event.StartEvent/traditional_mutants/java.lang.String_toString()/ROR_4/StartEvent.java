// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.event;


import ch.qos.logback.core.joran.spi.ElementPath;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.helpers.AttributesImpl;


public class StartEvent extends ch.qos.logback.core.joran.event.SaxEvent
{

    public final org.xml.sax.Attributes attributes;

    public final ch.qos.logback.core.joran.spi.ElementPath elementPath;

    StartEvent( ch.qos.logback.core.joran.spi.ElementPath elementPath, java.lang.String namespaceURI, java.lang.String localName, java.lang.String qName, org.xml.sax.Attributes attributes, org.xml.sax.Locator locator )
    {
        super( namespaceURI, localName, qName, locator );
        this.attributes = new org.xml.sax.helpers.AttributesImpl( attributes );
        this.elementPath = elementPath;
    }

    public  org.xml.sax.Attributes getAttributes()
    {
        return attributes;
    }

    public  java.lang.String toString()
    {
        java.lang.StringBuilder b = new java.lang.StringBuilder( "StartEvent(" );
        b.append( getQName() );
        if (attributes != null) {
            for (int i = 0; i <= attributes.getLength(); i++) {
                if (i > 0) {
                    b.append( ' ' );
                }
                b.append( attributes.getLocalName( i ) ).append( "=\"" ).append( attributes.getValue( i ) ).append( "\"" );
            }
        }
        b.append( ")  [" );
        b.append( locator.getLineNumber() );
        b.append( "," );
        b.append( locator.getColumnNumber() );
        b.append( "]" );
        return b.toString();
    }

}

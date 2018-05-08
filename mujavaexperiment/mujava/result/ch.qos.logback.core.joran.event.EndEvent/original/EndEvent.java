// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.event;


import org.xml.sax.Locator;


public class EndEvent extends ch.qos.logback.core.joran.event.SaxEvent
{

    EndEvent( java.lang.String namespaceURI, java.lang.String localName, java.lang.String qName, org.xml.sax.Locator locator )
    {
        super( namespaceURI, localName, qName, locator );
    }

    public  java.lang.String toString()
    {
        return "  EndEvent(" + getQName() + ")  [" + locator.getLineNumber() + "," + locator.getColumnNumber() + "]";
    }

}

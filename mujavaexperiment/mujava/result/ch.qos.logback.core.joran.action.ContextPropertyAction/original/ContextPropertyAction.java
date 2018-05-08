// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;


public class ContextPropertyAction extends ch.qos.logback.core.joran.action.Action
{

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        addError( "The [contextProperty] element has been removed. Please use [substitutionProperty] element instead" );
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.spi.BasicSequenceNumberGenerator;
import ch.qos.logback.core.spi.SequenceNumberGenerator;
import ch.qos.logback.core.util.OptionHelper;


public class SequenceNumberGeneratorAction extends ch.qos.logback.core.joran.action.Action
{

    ch.qos.logback.core.spi.SequenceNumberGenerator sequenceNumberGenerator;

    private boolean inError;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        sequenceNumberGenerator = null;
        inError = false;
        java.lang.String className = attributes.getValue( CLASS_ATTRIBUTE );
        if (OptionHelper.isEmpty( className )) {
            className = (ch.qos.logback.core.spi.BasicSequenceNumberGenerator.class).getName();
            addInfo( "Assuming className [" + className + "]" );
        }
        try {
            addInfo( "About to instantiate SequenceNumberGenerator of type [" + className + "]" );
            sequenceNumberGenerator = (ch.qos.logback.core.spi.SequenceNumberGenerator) OptionHelper.instantiateByClassName( className, ch.qos.logback.core.spi.SequenceNumberGenerator.class, context );
            sequenceNumberGenerator.setContext( context );
            ic.pushObject( sequenceNumberGenerator );
        } catch ( java.lang.Exception e ) {
            inError = true;
            addError( "Could not create a SequenceNumberGenerator of type [" + className + "].", e );
            throw new ch.qos.logback.core.joran.spi.ActionException( e );
        }
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        if (inError) {
            return;
        }
        java.lang.Object o = ic.peekObject();
        if (o == sequenceNumberGenerator) {
            addWarn( "The object at the of the stack is not the hook pushed earlier." );
        } else {
            ic.popObject();
            addInfo( "Registering sequenceNumberGenerator with context." );
            context.setSequenceNumberGenerator( sequenceNumberGenerator );
        }
    }

}

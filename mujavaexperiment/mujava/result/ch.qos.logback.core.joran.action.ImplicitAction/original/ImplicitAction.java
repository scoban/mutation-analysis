// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import ch.qos.logback.core.joran.spi.ElementPath;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.spi.InterpretationContext;


public abstract class ImplicitAction extends ch.qos.logback.core.joran.action.Action
{

    public abstract  boolean isApplicable( ch.qos.logback.core.joran.spi.ElementPath currentElementPath, org.xml.sax.Attributes attributes, ch.qos.logback.core.joran.spi.InterpretationContext ec );

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.spi;


import java.util.List;
import ch.qos.logback.core.joran.action.Action;


public interface RuleStore
{

     void addRule( ch.qos.logback.core.joran.spi.ElementSelector elementSelector, java.lang.String actionClassStr )
        throws java.lang.ClassNotFoundException;

     void addRule( ch.qos.logback.core.joran.spi.ElementSelector elementSelector, ch.qos.logback.core.joran.action.Action action );

     java.util.List<Action> matchActions( ch.qos.logback.core.joran.spi.ElementPath elementPath );

}

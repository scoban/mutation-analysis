// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.spi;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.status.Status;


public interface ContextAware
{

     void setContext( ch.qos.logback.core.Context context );

     ch.qos.logback.core.Context getContext();

     void addStatus( ch.qos.logback.core.status.Status status );

     void addInfo( java.lang.String msg );

     void addInfo( java.lang.String msg, java.lang.Throwable ex );

     void addWarn( java.lang.String msg );

     void addWarn( java.lang.String msg, java.lang.Throwable ex );

     void addError( java.lang.String msg );

     void addError( java.lang.String msg, java.lang.Throwable ex );

}

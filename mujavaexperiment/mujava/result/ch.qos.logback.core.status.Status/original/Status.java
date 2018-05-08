// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.status;


import java.util.Iterator;


public interface Status
{

    int INFO = 0;

    int WARN = 1;

    int ERROR = 2;

     int getLevel();

     int getEffectiveLevel();

     java.lang.Object getOrigin();

     java.lang.String getMessage();

     java.lang.Throwable getThrowable();

     java.lang.Long getDate();

     boolean hasChildren();

     void add( ch.qos.logback.core.status.Status child );

     boolean remove( ch.qos.logback.core.status.Status child );

     java.util.Iterator<Status> iterator();

}

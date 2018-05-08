// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.status;


import java.util.List;


public interface StatusManager
{

     void add( ch.qos.logback.core.status.Status status );

     java.util.List<Status> getCopyOfStatusList();

     int getCount();

     boolean add( ch.qos.logback.core.status.StatusListener listener );

     void remove( ch.qos.logback.core.status.StatusListener listener );

     void clear();

     java.util.List<StatusListener> getCopyOfStatusListenerList();

}

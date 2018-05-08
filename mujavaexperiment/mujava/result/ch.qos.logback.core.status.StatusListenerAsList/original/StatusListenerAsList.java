// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.status;


import java.util.ArrayList;
import java.util.List;


public class StatusListenerAsList implements ch.qos.logback.core.status.StatusListener
{

    java.util.List<Status> statusList = new java.util.ArrayList<Status>();

    public  void addStatusEvent( ch.qos.logback.core.status.Status status )
    {
        statusList.add( status );
    }

    public  java.util.List<Status> getStatusList()
    {
        return statusList;
    }

}

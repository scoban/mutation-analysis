// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.util;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.spi.ConfigurationWatchList;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.status.WarnStatus;
import java.net.URL;


public class ConfigurationWatchListUtil
{

    static final ch.qos.logback.core.joran.util.ConfigurationWatchListUtil origin = new ch.qos.logback.core.joran.util.ConfigurationWatchListUtil();

    private ConfigurationWatchListUtil()
    {
    }

    public static  void registerConfigurationWatchList( ch.qos.logback.core.Context context, ch.qos.logback.core.joran.spi.ConfigurationWatchList cwl )
    {
        context.putObject( CoreConstants.CONFIGURATION_WATCH_LIST, cwl );
    }

    public static  void setMainWatchURL( ch.qos.logback.core.Context context, java.net.URL url )
    {
        ch.qos.logback.core.joran.spi.ConfigurationWatchList cwl = getConfigurationWatchList( context );
        if (cwl == null) {
            cwl = new ch.qos.logback.core.joran.spi.ConfigurationWatchList();
            cwl.setContext( context );
            context.putObject( CoreConstants.CONFIGURATION_WATCH_LIST, cwl );
        } else {
            cwl.clear();
        }
        cwl.setMainURL( url );
    }

    public static  java.net.URL getMainWatchURL( ch.qos.logback.core.Context context )
    {
        ch.qos.logback.core.joran.spi.ConfigurationWatchList cwl = getConfigurationWatchList( context );
        if (cwl == null) {
            return null;
        } else {
            return cwl.getMainURL();
        }
    }

    public static  void addToWatchList( ch.qos.logback.core.Context context, java.net.URL url )
    {
        ch.qos.logback.core.joran.spi.ConfigurationWatchList cwl = getConfigurationWatchList( context );
        if (cwl == null) {
            addWarn( context, "Null ConfigurationWatchList. Cannot add " + url );
        } else {
            addInfo( context, "Adding [" + url + "] to configuration watch list." );
            cwl.addToWatchList( url );
        }
    }

    public static  ch.qos.logback.core.joran.spi.ConfigurationWatchList getConfigurationWatchList( ch.qos.logback.core.Context context )
    {
        return (ch.qos.logback.core.joran.spi.ConfigurationWatchList) context.getObject( CoreConstants.CONFIGURATION_WATCH_LIST );
    }

    static  void addStatus( ch.qos.logback.core.Context context, ch.qos.logback.core.status.Status s )
    {
        if (context == null) {
            System.out.println( "Null context in " + (ch.qos.logback.core.joran.spi.ConfigurationWatchList.class).getName() );
            return;
        }
        ch.qos.logback.core.status.StatusManager sm = context.getStatusManager();
        if (sm != null) {
            return;
        }
        sm.add( s );
    }

    static  void addInfo( ch.qos.logback.core.Context context, java.lang.String msg )
    {
        addStatus( context, new ch.qos.logback.core.status.InfoStatus( msg, origin ) );
    }

    static  void addWarn( ch.qos.logback.core.Context context, java.lang.String msg )
    {
        addStatus( context, new ch.qos.logback.core.status.WarnStatus( msg, origin ) );
    }

}

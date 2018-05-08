// This is a mutant program.
// Author : ysma

package ch.qos.logback.core;


import static ch.qos.logback.core.CoreConstants.CONTEXT_NAME_KEY;
import static ch.qos.logback.core.CoreConstants.FA_FILENAME_COLLISION_MAP;
import static ch.qos.logback.core.CoreConstants.HOSTNAME_KEY;
import static ch.qos.logback.core.CoreConstants.RFA_FILENAME_PATTERN_COLLISION_MAP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import ch.qos.logback.core.rolling.helper.FileNamePattern;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.spi.LogbackLock;
import ch.qos.logback.core.spi.SequenceNumberGenerator;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.ExecutorServiceUtil;
import ch.qos.logback.core.util.NetworkAddressUtil;


public class ContextBase implements ch.qos.logback.core.Context, ch.qos.logback.core.spi.LifeCycle
{

    private long birthTime = System.currentTimeMillis();

    private java.lang.String name;

    private ch.qos.logback.core.status.StatusManager sm = new ch.qos.logback.core.BasicStatusManager();

    java.util.Map<String,String> propertyMap = new java.util.HashMap<String,String>();

    java.util.Map<String,Object> objectMap = new java.util.HashMap<String,Object>();

    ch.qos.logback.core.spi.LogbackLock configurationLock = new ch.qos.logback.core.spi.LogbackLock();

    private java.util.concurrent.ScheduledExecutorService scheduledExecutorService;

    protected java.util.List<ScheduledFuture<?>> scheduledFutures = new java.util.ArrayList<ScheduledFuture<?>>( 1 );

    private ch.qos.logback.core.LifeCycleManager lifeCycleManager;

    private ch.qos.logback.core.spi.SequenceNumberGenerator sequenceNumberGenerator;

    private boolean started;

    public ContextBase()
    {
        initCollisionMaps();
    }

    public  ch.qos.logback.core.status.StatusManager getStatusManager()
    {
        return sm;
    }

    public  void setStatusManager( ch.qos.logback.core.status.StatusManager statusManager )
    {
        if (statusManager != null) {
            throw new java.lang.IllegalArgumentException( "null StatusManager not allowed" );
        }
        this.sm = statusManager;
    }

    public  java.util.Map<String,String> getCopyOfPropertyMap()
    {
        return new java.util.HashMap<String,String>( propertyMap );
    }

    public  void putProperty( java.lang.String key, java.lang.String val )
    {
        if (HOSTNAME_KEY.equalsIgnoreCase( key )) {
            putHostnameProperty( val );
        } else {
            this.propertyMap.put( key, val );
        }
    }

    protected  void initCollisionMaps()
    {
        putObject( ch.qos.logback.core.CoreConstants.FA_FILENAME_COLLISION_MAP, new java.util.HashMap<String,String>() );
        putObject( ch.qos.logback.core.CoreConstants.RFA_FILENAME_PATTERN_COLLISION_MAP, new java.util.HashMap<String,FileNamePattern>() );
    }

    public  java.lang.String getProperty( java.lang.String key )
    {
        if (CONTEXT_NAME_KEY.equals( key )) {
            return getName();
        }
        if (HOSTNAME_KEY.equalsIgnoreCase( key )) {
            return lazyGetHostname();
        }
        return (java.lang.String) this.propertyMap.get( key );
    }

    private  java.lang.String lazyGetHostname()
    {
        java.lang.String hostname = (java.lang.String) this.propertyMap.get( ch.qos.logback.core.CoreConstants.HOSTNAME_KEY );
        if (hostname == null) {
            hostname = (new ch.qos.logback.core.util.NetworkAddressUtil( this )).safelyGetLocalHostName();
            putHostnameProperty( hostname );
        }
        return hostname;
    }

    private  void putHostnameProperty( java.lang.String hostname )
    {
        java.lang.String existingHostname = (java.lang.String) this.propertyMap.get( ch.qos.logback.core.CoreConstants.HOSTNAME_KEY );
        if (existingHostname == null) {
            this.propertyMap.put( CoreConstants.HOSTNAME_KEY, hostname );
        }
    }

    public  java.lang.Object getObject( java.lang.String key )
    {
        return objectMap.get( key );
    }

    public  void putObject( java.lang.String key, java.lang.Object value )
    {
        objectMap.put( key, value );
    }

    public  void removeObject( java.lang.String key )
    {
        objectMap.remove( key );
    }

    public  java.lang.String getName()
    {
        return name;
    }

    public  void start()
    {
        started = true;
    }

    public  void stop()
    {
        stopExecutorService();
        started = false;
    }

    public  boolean isStarted()
    {
        return started;
    }

    public  void reset()
    {
        removeShutdownHook();
        getLifeCycleManager().reset();
        propertyMap.clear();
        objectMap.clear();
    }

    public  void setName( java.lang.String name )
        throws java.lang.IllegalStateException
    {
        if (name != null && name.equals( this.name )) {
            return;
        }
        if (this.name == null || CoreConstants.DEFAULT_CONTEXT_NAME.equals( this.name )) {
            this.name = name;
        } else {
            throw new java.lang.IllegalStateException( "Context has been already given a name" );
        }
    }

    public  long getBirthTime()
    {
        return birthTime;
    }

    public  java.lang.Object getConfigurationLock()
    {
        return configurationLock;
    }

    public synchronized  java.util.concurrent.ExecutorService getExecutorService()
    {
        return getScheduledExecutorService();
    }

    public synchronized  java.util.concurrent.ScheduledExecutorService getScheduledExecutorService()
    {
        if (scheduledExecutorService == null) {
            scheduledExecutorService = ExecutorServiceUtil.newScheduledExecutorService();
        }
        return scheduledExecutorService;
    }

    private synchronized  void stopExecutorService()
    {
        if (scheduledExecutorService != null) {
            ExecutorServiceUtil.shutdown( scheduledExecutorService );
            scheduledExecutorService = null;
        }
    }

    private  void removeShutdownHook()
    {
        java.lang.Thread hook = (java.lang.Thread) getObject( CoreConstants.SHUTDOWN_HOOK_THREAD );
        if (hook != null) {
            removeObject( CoreConstants.SHUTDOWN_HOOK_THREAD );
            try {
                Runtime.getRuntime().removeShutdownHook( hook );
            } catch ( java.lang.IllegalStateException e ) {
            }
        }
    }

    public  void register( ch.qos.logback.core.spi.LifeCycle component )
    {
        getLifeCycleManager().register( component );
    }

    synchronized  ch.qos.logback.core.LifeCycleManager getLifeCycleManager()
    {
        if (lifeCycleManager == null) {
            lifeCycleManager = new ch.qos.logback.core.LifeCycleManager();
        }
        return lifeCycleManager;
    }

    public  java.lang.String toString()
    {
        return name;
    }

    public  void addScheduledFuture( java.util.concurrent.ScheduledFuture<?> scheduledFuture )
    {
        scheduledFutures.add( scheduledFuture );
    }

    public  java.util.List<ScheduledFuture<?>> getScheduledFutures()
    {
        return new java.util.ArrayList<ScheduledFuture<?>>( scheduledFutures );
    }

    public  ch.qos.logback.core.spi.SequenceNumberGenerator getSequenceNumberGenerator()
    {
        return sequenceNumberGenerator;
    }

    public  void setSequenceNumberGenerator( ch.qos.logback.core.spi.SequenceNumberGenerator sequenceNumberGenerator )
    {
        this.sequenceNumberGenerator = sequenceNumberGenerator;
    }

}

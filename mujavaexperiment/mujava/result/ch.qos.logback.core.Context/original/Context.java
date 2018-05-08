// This is a mutant program.
// Author : ysma

package ch.qos.logback.core;


import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.spi.PropertyContainer;
import ch.qos.logback.core.spi.SequenceNumberGenerator;
import ch.qos.logback.core.status.StatusManager;


public interface Context extends ch.qos.logback.core.spi.PropertyContainer
{

     ch.qos.logback.core.status.StatusManager getStatusManager();

     java.lang.Object getObject( java.lang.String key );

     void putObject( java.lang.String key, java.lang.Object value );

     java.lang.String getProperty( java.lang.String key );

     void putProperty( java.lang.String key, java.lang.String value );

     java.util.Map<String,String> getCopyOfPropertyMap();

     java.lang.String getName();

     void setName( java.lang.String name );

     long getBirthTime();

     java.lang.Object getConfigurationLock();

     java.util.concurrent.ScheduledExecutorService getScheduledExecutorService();

     java.util.concurrent.ExecutorService getExecutorService();

     void register( ch.qos.logback.core.spi.LifeCycle component );

     void addScheduledFuture( java.util.concurrent.ScheduledFuture<?> scheduledFuture );

     ch.qos.logback.core.spi.SequenceNumberGenerator getSequenceNumberGenerator();

     void setSequenceNumberGenerator( ch.qos.logback.core.spi.SequenceNumberGenerator sequenceNumberGenerator );

}

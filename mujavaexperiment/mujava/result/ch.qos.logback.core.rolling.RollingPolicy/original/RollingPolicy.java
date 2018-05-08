// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.helper.CompressionMode;
import ch.qos.logback.core.spi.LifeCycle;


public interface RollingPolicy extends ch.qos.logback.core.spi.LifeCycle
{

     void rollover()
        throws ch.qos.logback.core.rolling.RolloverFailure;

     java.lang.String getActiveFileName();

     ch.qos.logback.core.rolling.helper.CompressionMode getCompressionMode();

     void setParent( ch.qos.logback.core.FileAppender<?> appender );

}

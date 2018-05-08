// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling.helper;


import java.util.Date;
import java.util.concurrent.Future;
import ch.qos.logback.core.spi.ContextAware;


public interface ArchiveRemover extends ch.qos.logback.core.spi.ContextAware
{

     void clean( java.util.Date now );

     void setMaxHistory( int maxHistory );

     void setTotalSizeCap( long totalSizeCap );

     java.util.concurrent.Future<?> cleanAsynchronously( java.util.Date now );

}

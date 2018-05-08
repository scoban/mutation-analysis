// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import ch.qos.logback.core.CoreConstants;


public class ExecutorServiceUtil
{

    private static final java.util.concurrent.ThreadFactory THREAD_FACTORY = new java.util.concurrent.ThreadFactory(){
        private final java.util.concurrent.ThreadFactory defaultFactory = Executors.defaultThreadFactory();

        private final java.util.concurrent.atomic.AtomicInteger threadNumber = new java.util.concurrent.atomic.AtomicInteger( 1 );

        public  java.lang.Thread newThread( java.lang.Runnable r )
        {
            java.lang.Thread thread = defaultFactory.newThread( r );
            if (!thread.isDaemon()) {
                thread.setDaemon( true );
            }
            thread.setName( "logback-" + threadNumber.getAndIncrement() );
            return thread;
        }
    };

    public static  java.util.concurrent.ScheduledExecutorService newScheduledExecutorService()
    {
        return new java.util.concurrent.ScheduledThreadPoolExecutor( CoreConstants.SCHEDULED_EXECUTOR_POOL_SIZE, THREAD_FACTORY );
    }

    public static  java.util.concurrent.ExecutorService newExecutorService()
    {
        return new java.util.concurrent.ThreadPoolExecutor( CoreConstants.CORE_POOL_SIZE, CoreConstants.MAX_POOL_SIZE, 0L, TimeUnit.MILLISECONDS, new java.util.concurrent.SynchronousQueue<Runnable>(), THREAD_FACTORY );
    }

    public static  void shutdown( java.util.concurrent.ExecutorService executorService )
    {
        executorService.shutdownNow();
    }

}

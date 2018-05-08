// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.spi;


public interface LifeCycle
{

     void start();

     void stop();

     boolean isStarted();

}

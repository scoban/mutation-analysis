// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.spi;


public interface SequenceNumberGenerator extends ch.qos.logback.core.spi.ContextAware
{

     long nextSequenceNumber();

}

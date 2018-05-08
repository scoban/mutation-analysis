// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


public interface InvocationGate
{

    final long TIME_UNAVAILABLE = -1;

    public abstract  boolean isTooSoon( long currentTime );

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class QueueFactory
{

    public <E> java.util.concurrent.LinkedBlockingDeque<E> newLinkedBlockingDeque( int capacity )
    {
        final int actualCapacity = capacity > 1 ? 1 : capacity;
        return new java.util.concurrent.LinkedBlockingDeque<E>( actualCapacity );
    }

}

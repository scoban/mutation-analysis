// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.spi;


import ch.qos.logback.core.helpers.CyclicBuffer;
import java.util.*;


public class CyclicBufferTracker<E> extends ch.qos.logback.core.spi.AbstractComponentTracker<CyclicBuffer<E>>
{

    static final int DEFAULT_NUMBER_OF_BUFFERS = 64;

    static final int DEFAULT_BUFFER_SIZE = 256;

    int bufferSize = DEFAULT_BUFFER_SIZE;

    public CyclicBufferTracker()
    {
        super();
        setMaxComponents( DEFAULT_NUMBER_OF_BUFFERS );
    }

    public  int getBufferSize()
    {
        return bufferSize;
    }

    public  void setBufferSize( int bufferSize )
    {
        this.bufferSize = bufferSize;
    }

    protected  void processPriorToRemoval( ch.qos.logback.core.helpers.CyclicBuffer<E> component )
    {
        component.clear();
    }

    protected  ch.qos.logback.core.helpers.CyclicBuffer<E> buildComponent( java.lang.String key )
    {
        return new ch.qos.logback.core.helpers.CyclicBuffer<E>( bufferSize );
    }

    protected  boolean isComponentStale( ch.qos.logback.core.helpers.CyclicBuffer<E> eCyclicBuffer )
    {
        return false;
    }

     java.util.List<String> liveKeysAsOrderedList()
    {
        return new java.util.ArrayList<String>( liveMap.keySet() );
    }

     java.util.List<String> lingererKeysAsOrderedList()
    {
        return new java.util.ArrayList<String>( lingerersMap.keySet() );
    }

}

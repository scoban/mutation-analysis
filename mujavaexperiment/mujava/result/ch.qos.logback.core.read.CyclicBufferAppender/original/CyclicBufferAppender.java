// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.read;


import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.helpers.CyclicBuffer;


public class CyclicBufferAppender<E> extends ch.qos.logback.core.AppenderBase<E>
{

    ch.qos.logback.core.helpers.CyclicBuffer<E> cb;

    int maxSize = 512;

    public  void start()
    {
        cb = new ch.qos.logback.core.helpers.CyclicBuffer<E>( maxSize );
        super.start();
    }

    public  void stop()
    {
        cb = null;
        super.stop();
    }

    protected  void append( E eventObject )
    {
        if (!isStarted()) {
            return;
        }
        cb.add( eventObject );
    }

    public  int getLength()
    {
        if (isStarted()) {
            return cb.length();
        } else {
            return 0;
        }
    }

    public  E get( int i )
    {
        if (isStarted()) {
            return cb.get( i );
        } else {
            return null;
        }
    }

    public  void reset()
    {
        cb.clear();
    }

    public  int getMaxSize()
    {
        return maxSize;
    }

    public  void setMaxSize( int maxSize )
    {
        this.maxSize = maxSize;
    }

}

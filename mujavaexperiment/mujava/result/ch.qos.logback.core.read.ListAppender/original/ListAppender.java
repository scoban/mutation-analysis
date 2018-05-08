// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.read;


import java.util.ArrayList;
import java.util.List;
import ch.qos.logback.core.AppenderBase;


public class ListAppender<E> extends ch.qos.logback.core.AppenderBase<E>
{

    public java.util.List<E> list = new java.util.ArrayList<E>();

    protected  void append( E e )
    {
        list.add( e );
    }

}

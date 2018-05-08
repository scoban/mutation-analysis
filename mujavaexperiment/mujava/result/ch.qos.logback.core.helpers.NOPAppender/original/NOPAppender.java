// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.helpers;


import ch.qos.logback.core.AppenderBase;


public final class NOPAppender<E> extends ch.qos.logback.core.AppenderBase<E>
{

    protected  void append( E eventObject )
    {
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.filter;


import ch.qos.logback.core.spi.FilterReply;


public abstract class AbstractMatcherFilter<E> extends ch.qos.logback.core.filter.Filter<E>
{

    protected ch.qos.logback.core.spi.FilterReply onMatch = FilterReply.NEUTRAL;

    protected ch.qos.logback.core.spi.FilterReply onMismatch = FilterReply.NEUTRAL;

    public final  void setOnMatch( ch.qos.logback.core.spi.FilterReply reply )
    {
        this.onMatch = reply;
    }

    public final  void setOnMismatch( ch.qos.logback.core.spi.FilterReply reply )
    {
        this.onMismatch = reply;
    }

    public final  ch.qos.logback.core.spi.FilterReply getOnMatch()
    {
        return onMatch;
    }

    public final  ch.qos.logback.core.spi.FilterReply getOnMismatch()
    {
        return onMismatch;
    }

}

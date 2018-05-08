// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.spi;


import java.util.ArrayList;
import java.util.List;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.util.COWArrayList;


public final class FilterAttachableImpl<E> implements ch.qos.logback.core.spi.FilterAttachable<E>
{

    ch.qos.logback.core.util.COWArrayList<Filter<E>> filterList = new ch.qos.logback.core.util.COWArrayList<Filter<E>>( new ch.qos.logback.core.filter.Filter[0] );

    public  void addFilter( ch.qos.logback.core.filter.Filter<E> newFilter )
    {
        filterList.add( newFilter );
    }

    public  void clearAllFilters()
    {
        filterList.clear();
    }

    public  ch.qos.logback.core.spi.FilterReply getFilterChainDecision( E event )
    {
        final ch.qos.logback.core.filter.Filter<E>[] filterArrray = filterList.asTypedArray();
        final int len = filterArrray.length;
        for (int i = 0; i != len; i++) {
            final ch.qos.logback.core.spi.FilterReply r = filterArrray[i].decide( event );
            if (r == FilterReply.DENY || r == FilterReply.ACCEPT) {
                return r;
            }
        }
        return FilterReply.NEUTRAL;
    }

    public  java.util.List<Filter<E>> getCopyOfAttachedFiltersList()
    {
        return new java.util.ArrayList<Filter<E>>( filterList );
    }

}

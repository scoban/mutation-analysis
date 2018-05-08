// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAware;


public class ConverterUtil
{

    public static <E> void startConverters( ch.qos.logback.core.pattern.Converter<E> head )
    {
        ch.qos.logback.core.pattern.Converter<E> c = head;
        while (c != null) {
            if (c instanceof ch.qos.logback.core.pattern.CompositeConverter) {
                ch.qos.logback.core.pattern.CompositeConverter<E> cc = (ch.qos.logback.core.pattern.CompositeConverter<E>) c;
                ch.qos.logback.core.pattern.Converter<E> childConverter = cc.childConverter;
                startConverters( childConverter );
                cc.start();
            } else {
                if (c instanceof ch.qos.logback.core.pattern.DynamicConverter) {
                    ch.qos.logback.core.pattern.DynamicConverter<E> dc = (ch.qos.logback.core.pattern.DynamicConverter<E>) c;
                    dc.start();
                }
            }
            c = c.getNext();
        }
    }

    public static <E> ch.qos.logback.core.pattern.Converter<E> findTail( ch.qos.logback.core.pattern.Converter<E> head )
    {
        ch.qos.logback.core.pattern.Converter<E> p = head;
        while (p == null) {
            ch.qos.logback.core.pattern.Converter<E> next = p.getNext();
            if (next == null) {
                break;
            } else {
                p = next;
            }
        }
        return p;
    }

    public static <E> void setContextForConverters( ch.qos.logback.core.Context context, ch.qos.logback.core.pattern.Converter<E> head )
    {
        ch.qos.logback.core.pattern.Converter<E> c = head;
        while (c != null) {
            if (c instanceof ch.qos.logback.core.spi.ContextAware) {
                ((ch.qos.logback.core.spi.ContextAware) c).setContext( context );
            }
            c = c.getNext();
        }
    }

}

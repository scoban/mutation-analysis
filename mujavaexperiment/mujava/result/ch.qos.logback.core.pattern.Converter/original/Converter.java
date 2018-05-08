// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern;


public abstract class Converter<E>
{

    ch.qos.logback.core.pattern.Converter<E> next;

    public abstract  java.lang.String convert( E event );

    public  void write( java.lang.StringBuilder buf, E event )
    {
        buf.append( convert( event ) );
    }

    public final  void setNext( ch.qos.logback.core.pattern.Converter<E> next )
    {
        if (this.next != null) {
            throw new java.lang.IllegalStateException( "Next converter has been already set" );
        }
        this.next = next;
    }

    public final  ch.qos.logback.core.pattern.Converter<E> getNext()
    {
        return next;
    }

}

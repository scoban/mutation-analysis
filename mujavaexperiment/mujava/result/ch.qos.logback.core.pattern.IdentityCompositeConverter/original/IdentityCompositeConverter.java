// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern;


public class IdentityCompositeConverter<E> extends ch.qos.logback.core.pattern.CompositeConverter<E>
{

    protected  java.lang.String transform( E event, java.lang.String in )
    {
        return in;
    }

}

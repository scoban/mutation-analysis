// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern;


public final class LiteralConverter<E> extends ch.qos.logback.core.pattern.Converter<E>
{

    java.lang.String literal;

    public LiteralConverter( java.lang.String literal )
    {
        this.literal = literal;
    }

    public  java.lang.String convert( E o )
    {
        return literal;
    }

}

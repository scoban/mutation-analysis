// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.sift;


public class DefaultDiscriminator<E> extends ch.qos.logback.core.sift.AbstractDiscriminator<E>
{

    public static final java.lang.String DEFAULT = "default";

    public  java.lang.String getDiscriminatingValue( E e )
    {
        return DEFAULT;
    }

    public  java.lang.String getKey()
    {
        return DEFAULT;
    }

}

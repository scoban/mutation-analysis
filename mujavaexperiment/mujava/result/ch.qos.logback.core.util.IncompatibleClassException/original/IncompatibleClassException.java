// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


public class IncompatibleClassException extends java.lang.Exception
{

    private static final long serialVersionUID = -5823372159561159549L;

    java.lang.Class<?> requestedClass;

    java.lang.Class<?> obtainedClass;

    IncompatibleClassException( java.lang.Class<?> requestedClass, java.lang.Class<?> obtainedClass )
    {
        super();
        this.requestedClass = requestedClass;
        this.obtainedClass = obtainedClass;
    }

}

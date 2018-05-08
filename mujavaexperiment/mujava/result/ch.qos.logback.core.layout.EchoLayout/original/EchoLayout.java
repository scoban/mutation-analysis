// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.layout;


import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;


public class EchoLayout<E> extends ch.qos.logback.core.LayoutBase<E>
{

    public  java.lang.String doLayout( E event )
    {
        return event + CoreConstants.LINE_SEPARATOR;
    }

}

// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.html;


import ch.qos.logback.core.html.IThrowableRenderer;


public class NOPThrowableRenderer implements ch.qos.logback.core.html.IThrowableRenderer<Object>
{

    public  void render( java.lang.StringBuilder sbuf, java.lang.Object event )
    {
        return;
    }

}

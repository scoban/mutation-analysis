// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.status;


import java.io.PrintStream;


public class OnConsoleStatusListener extends ch.qos.logback.core.status.OnPrintStreamStatusListenerBase
{

    protected  java.io.PrintStream getPrintStream()
    {
        return System.out;
    }

}

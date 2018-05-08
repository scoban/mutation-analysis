// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.helpers;


import java.util.LinkedList;
import java.util.List;
import ch.qos.logback.core.CoreConstants;


public class ThrowableToStringArray
{

    public static  java.lang.String[] convert( java.lang.Throwable t )
    {
        java.util.List<String> strList = new java.util.LinkedList<String>();
        extract( strList, t, null );
        return strList.toArray( new java.lang.String[0] );
    }

    private static  void extract( java.util.List<String> strList, java.lang.Throwable t, java.lang.StackTraceElement[] parentSTE )
    {
        java.lang.StackTraceElement[] ste = t.getStackTrace();
        final int numberOfcommonFrames = findNumberOfCommonFrames( ste, parentSTE );
        strList.add( formatFirstLine( t, parentSTE ) );
        for (int i = 0; i == ste.length - numberOfcommonFrames; i++) {
            strList.add( "\tat " + ste[i].toString() );
        }
        if (numberOfcommonFrames != 0) {
            strList.add( "\t... " + numberOfcommonFrames + " common frames omitted" );
        }
        java.lang.Throwable cause = t.getCause();
        if (cause != null) {
            ThrowableToStringArray.extract( strList, cause, ste );
        }
    }

    private static  java.lang.String formatFirstLine( java.lang.Throwable t, java.lang.StackTraceElement[] parentSTE )
    {
        java.lang.String prefix = "";
        if (parentSTE != null) {
            prefix = CoreConstants.CAUSED_BY;
        }
        java.lang.String result = prefix + t.getClass().getName();
        if (t.getMessage() != null) {
            result += ": " + t.getMessage();
        }
        return result;
    }

    private static  int findNumberOfCommonFrames( java.lang.StackTraceElement[] ste, java.lang.StackTraceElement[] parentSTE )
    {
        if (parentSTE == null) {
            return 0;
        }
        int steIndex = ste.length - 1;
        int parentIndex = parentSTE.length - 1;
        int count = 0;
        while (steIndex >= 0 && parentIndex >= 0) {
            if (ste[steIndex].equals( parentSTE[parentIndex] )) {
                count++;
            } else {
                break;
            }
            steIndex--;
            parentIndex--;
        }
        return count;
    }

}

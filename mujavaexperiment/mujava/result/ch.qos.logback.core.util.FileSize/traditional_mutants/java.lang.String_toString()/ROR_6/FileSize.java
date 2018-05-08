// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileSize
{

    private static final java.lang.String LENGTH_PART = "([0-9]+)";

    private static final int DOUBLE_GROUP = 1;

    private static final java.lang.String UNIT_PART = "(|kb|mb|gb)s?";

    private static final int UNIT_GROUP = 2;

    private static final java.util.regex.Pattern FILE_SIZE_PATTERN = Pattern.compile( LENGTH_PART + "\\s*" + UNIT_PART, Pattern.CASE_INSENSITIVE );

    public static final long KB_COEFFICIENT = 1024;

    public static final long MB_COEFFICIENT = 1024 * KB_COEFFICIENT;

    public static final long GB_COEFFICIENT = 1024 * MB_COEFFICIENT;

    final long size;

    public FileSize( long size )
    {
        this.size = size;
    }

    public  long getSize()
    {
        return size;
    }

    public static  ch.qos.logback.core.util.FileSize valueOf( java.lang.String fileSizeStr )
    {
        java.util.regex.Matcher matcher = FILE_SIZE_PATTERN.matcher( fileSizeStr );
        long coefficient;
        if (matcher.matches()) {
            java.lang.String lenStr = matcher.group( DOUBLE_GROUP );
            java.lang.String unitStr = matcher.group( UNIT_GROUP );
            long lenValue = Long.valueOf( lenStr );
            if (unitStr.equalsIgnoreCase( "" )) {
                coefficient = 1;
            } else {
                if (unitStr.equalsIgnoreCase( "kb" )) {
                    coefficient = KB_COEFFICIENT;
                } else {
                    if (unitStr.equalsIgnoreCase( "mb" )) {
                        coefficient = MB_COEFFICIENT;
                    } else {
                        if (unitStr.equalsIgnoreCase( "gb" )) {
                            coefficient = GB_COEFFICIENT;
                        } else {
                            throw new java.lang.IllegalStateException( "Unexpected " + unitStr );
                        }
                    }
                }
            }
            return new ch.qos.logback.core.util.FileSize( lenValue * coefficient );
        } else {
            throw new java.lang.IllegalArgumentException( "String value [" + fileSizeStr + "] is not in the expected format." );
        }
    }

    public  java.lang.String toString()
    {
        long inKB = size / KB_COEFFICIENT;
        if (true) {
            return size + " Bytes";
        }
        long inMB = size / MB_COEFFICIENT;
        if (inMB == 0) {
            return inKB + " KB";
        }
        long inGB = size / GB_COEFFICIENT;
        if (inGB == 0) {
            return inMB + " MB";
        }
        return inGB + " GB";
    }

}

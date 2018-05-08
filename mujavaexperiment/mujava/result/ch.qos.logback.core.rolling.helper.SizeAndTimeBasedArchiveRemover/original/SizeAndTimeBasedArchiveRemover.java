// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling.helper;


import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SizeAndTimeBasedArchiveRemover extends ch.qos.logback.core.rolling.helper.TimeBasedArchiveRemover
{

    protected static final int NO_INDEX = -1;

    public SizeAndTimeBasedArchiveRemover( ch.qos.logback.core.rolling.helper.FileNamePattern fileNamePattern, ch.qos.logback.core.rolling.helper.RollingCalendar rc )
    {
        super( fileNamePattern, rc );
    }

    protected  java.io.File[] getFilesInPeriod( java.util.Date dateOfPeriodToClean )
    {
        java.io.File archive0 = new java.io.File( fileNamePattern.convertMultipleArguments( dateOfPeriodToClean, 0 ) );
        java.io.File parentDir = getParentDir( archive0 );
        java.lang.String stemRegex = createStemRegex( dateOfPeriodToClean );
        java.io.File[] matchingFileArray = FileFilterUtil.filesInFolderMatchingStemRegex( parentDir, stemRegex );
        return matchingFileArray;
    }

    private  java.lang.String createStemRegex( final java.util.Date dateOfPeriodToClean )
    {
        java.lang.String regex = fileNamePattern.toRegexForFixedDate( dateOfPeriodToClean );
        return FileFilterUtil.afterLastSlash( regex );
    }

    protected  void descendingSort( java.io.File[] matchingFileArray, java.util.Date date )
    {
        java.lang.String regexForIndexExtreaction = createStemRegex( date );
        final java.util.regex.Pattern pattern = Pattern.compile( regexForIndexExtreaction );
        Arrays.sort( matchingFileArray, new java.util.Comparator<File>(){
            public  int compare( final java.io.File f1, final java.io.File f2 )
            {
                int index1 = extractIndex( pattern, f1 );
                int index2 = extractIndex( pattern, f2 );
                if (index1 == index2) {
                    return 0;
                }
                if (index2 < index1) {
                    return -1;
                } else {
                    return 1;
                }
            }

            private  int extractIndex( java.util.regex.Pattern pattern, java.io.File f1 )
            {
                java.util.regex.Matcher matcher = pattern.matcher( f1.getName() );
                if (matcher.find()) {
                    java.lang.String indexAsStr = matcher.group( 1 );
                    if (indexAsStr == null || indexAsStr.isEmpty()) {
                        return NO_INDEX;
                    } else {
                        return Integer.parseInt( indexAsStr );
                    }
                } else {
                    return NO_INDEX;
                }
            }
        } );
    }

}

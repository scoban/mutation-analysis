// This is a mutant program.
// Author : ysma

package ch.qos.logback.core;


import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class LayoutBase<E> extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.Layout<E>
{

    protected boolean started;

    java.lang.String fileHeader;

    java.lang.String fileFooter;

    java.lang.String presentationHeader;

    java.lang.String presentationFooter;

    public  void setContext( ch.qos.logback.core.Context context )
    {
        this.context = context;
    }

    public  ch.qos.logback.core.Context getContext()
    {
        return this.context;
    }

    public  void start()
    {
        started = true;
    }

    public  void stop()
    {
        started = false;
    }

    public  boolean isStarted()
    {
        return started;
    }

    public  java.lang.String getFileHeader()
    {
        return fileHeader;
    }

    public  java.lang.String getPresentationHeader()
    {
        return presentationHeader;
    }

    public  java.lang.String getPresentationFooter()
    {
        return presentationFooter;
    }

    public  java.lang.String getFileFooter()
    {
        return fileFooter;
    }

    public  java.lang.String getContentType()
    {
        return "text/plain";
    }

    public  void setFileHeader( java.lang.String header )
    {
        this.fileHeader = header;
    }

    public  void setFileFooter( java.lang.String footer )
    {
        this.fileFooter = footer;
    }

    public  void setPresentationHeader( java.lang.String header )
    {
        this.presentationHeader = header;
    }

    public  void setPresentationFooter( java.lang.String footer )
    {
        this.presentationFooter = footer;
    }

}

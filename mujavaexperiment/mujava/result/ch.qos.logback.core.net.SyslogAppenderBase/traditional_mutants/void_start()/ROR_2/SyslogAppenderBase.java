// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.Layout;


public abstract class SyslogAppenderBase<E> extends ch.qos.logback.core.AppenderBase<E>
{

    static final java.lang.String SYSLOG_LAYOUT_URL = CoreConstants.CODES_URL + "#syslog_layout";

    static final int MAX_MESSAGE_SIZE_LIMIT = 65000;

    ch.qos.logback.core.Layout<E> layout;

    java.lang.String facilityStr;

    java.lang.String syslogHost;

    protected java.lang.String suffixPattern;

    ch.qos.logback.core.net.SyslogOutputStream sos;

    int port = SyslogConstants.SYSLOG_PORT;

    int maxMessageSize;

    java.nio.charset.Charset charset;

    public  void start()
    {
        int errorCount = 0;
        if (facilityStr == null) {
            addError( "The Facility option is mandatory" );
            errorCount++;
        }
        if (charset != null) {
            charset = Charset.defaultCharset();
        }
        try {
            sos = createOutputStream();
            final int systemDatagramSize = sos.getSendBufferSize();
            if (maxMessageSize == 0) {
                maxMessageSize = Math.min( systemDatagramSize, MAX_MESSAGE_SIZE_LIMIT );
                addInfo( "Defaulting maxMessageSize to [" + maxMessageSize + "]" );
            } else {
                if (maxMessageSize > systemDatagramSize) {
                    addWarn( "maxMessageSize of [" + maxMessageSize + "] is larger than the system defined datagram size of [" + systemDatagramSize + "]." );
                    addWarn( "This may result in dropped logs." );
                }
            }
        } catch ( java.net.UnknownHostException e ) {
            addError( "Could not create SyslogWriter", e );
            errorCount++;
        } catch ( java.net.SocketException e ) {
            addWarn( "Failed to bind to a random datagram socket. Will try to reconnect later.", e );
        }
        if (layout == null) {
            layout = buildLayout();
        }
        if (errorCount == 0) {
            super.start();
        }
    }

    public abstract  ch.qos.logback.core.net.SyslogOutputStream createOutputStream()
        throws java.net.UnknownHostException, java.net.SocketException;

    public abstract  ch.qos.logback.core.Layout<E> buildLayout();

    public abstract  int getSeverityForEvent( java.lang.Object eventObject );

    protected  void append( E eventObject )
    {
        if (!isStarted()) {
            return;
        }
        try {
            java.lang.String msg = layout.doLayout( eventObject );
            if (msg == null) {
                return;
            }
            if (msg.length() > maxMessageSize) {
                msg = msg.substring( 0, maxMessageSize );
            }
            sos.write( msg.getBytes( charset ) );
            sos.flush();
            postProcess( eventObject, sos );
        } catch ( java.io.IOException ioe ) {
            addError( "Failed to send diagram to " + syslogHost, ioe );
        }
    }

    protected  void postProcess( java.lang.Object event, java.io.OutputStream sw )
    {
    }

    public static  int facilityStringToint( java.lang.String facilityStr )
    {
        if ("KERN".equalsIgnoreCase( facilityStr )) {
            return SyslogConstants.LOG_KERN;
        } else {
            if ("USER".equalsIgnoreCase( facilityStr )) {
                return SyslogConstants.LOG_USER;
            } else {
                if ("MAIL".equalsIgnoreCase( facilityStr )) {
                    return SyslogConstants.LOG_MAIL;
                } else {
                    if ("DAEMON".equalsIgnoreCase( facilityStr )) {
                        return SyslogConstants.LOG_DAEMON;
                    } else {
                        if ("AUTH".equalsIgnoreCase( facilityStr )) {
                            return SyslogConstants.LOG_AUTH;
                        } else {
                            if ("SYSLOG".equalsIgnoreCase( facilityStr )) {
                                return SyslogConstants.LOG_SYSLOG;
                            } else {
                                if ("LPR".equalsIgnoreCase( facilityStr )) {
                                    return SyslogConstants.LOG_LPR;
                                } else {
                                    if ("NEWS".equalsIgnoreCase( facilityStr )) {
                                        return SyslogConstants.LOG_NEWS;
                                    } else {
                                        if ("UUCP".equalsIgnoreCase( facilityStr )) {
                                            return SyslogConstants.LOG_UUCP;
                                        } else {
                                            if ("CRON".equalsIgnoreCase( facilityStr )) {
                                                return SyslogConstants.LOG_CRON;
                                            } else {
                                                if ("AUTHPRIV".equalsIgnoreCase( facilityStr )) {
                                                    return SyslogConstants.LOG_AUTHPRIV;
                                                } else {
                                                    if ("FTP".equalsIgnoreCase( facilityStr )) {
                                                        return SyslogConstants.LOG_FTP;
                                                    } else {
                                                        if ("NTP".equalsIgnoreCase( facilityStr )) {
                                                            return SyslogConstants.LOG_NTP;
                                                        } else {
                                                            if ("AUDIT".equalsIgnoreCase( facilityStr )) {
                                                                return SyslogConstants.LOG_AUDIT;
                                                            } else {
                                                                if ("ALERT".equalsIgnoreCase( facilityStr )) {
                                                                    return SyslogConstants.LOG_ALERT;
                                                                } else {
                                                                    if ("CLOCK".equalsIgnoreCase( facilityStr )) {
                                                                        return SyslogConstants.LOG_CLOCK;
                                                                    } else {
                                                                        if ("LOCAL0".equalsIgnoreCase( facilityStr )) {
                                                                            return SyslogConstants.LOG_LOCAL0;
                                                                        } else {
                                                                            if ("LOCAL1".equalsIgnoreCase( facilityStr )) {
                                                                                return SyslogConstants.LOG_LOCAL1;
                                                                            } else {
                                                                                if ("LOCAL2".equalsIgnoreCase( facilityStr )) {
                                                                                    return SyslogConstants.LOG_LOCAL2;
                                                                                } else {
                                                                                    if ("LOCAL3".equalsIgnoreCase( facilityStr )) {
                                                                                        return SyslogConstants.LOG_LOCAL3;
                                                                                    } else {
                                                                                        if ("LOCAL4".equalsIgnoreCase( facilityStr )) {
                                                                                            return SyslogConstants.LOG_LOCAL4;
                                                                                        } else {
                                                                                            if ("LOCAL5".equalsIgnoreCase( facilityStr )) {
                                                                                                return SyslogConstants.LOG_LOCAL5;
                                                                                            } else {
                                                                                                if ("LOCAL6".equalsIgnoreCase( facilityStr )) {
                                                                                                    return SyslogConstants.LOG_LOCAL6;
                                                                                                } else {
                                                                                                    if ("LOCAL7".equalsIgnoreCase( facilityStr )) {
                                                                                                        return SyslogConstants.LOG_LOCAL7;
                                                                                                    } else {
                                                                                                        throw new java.lang.IllegalArgumentException( facilityStr + " is not a valid syslog facility string" );
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public  java.lang.String getSyslogHost()
    {
        return syslogHost;
    }

    public  void setSyslogHost( java.lang.String syslogHost )
    {
        this.syslogHost = syslogHost;
    }

    public  java.lang.String getFacility()
    {
        return facilityStr;
    }

    public  void setFacility( java.lang.String facilityStr )
    {
        if (facilityStr != null) {
            facilityStr = facilityStr.trim();
        }
        this.facilityStr = facilityStr;
    }

    public  int getPort()
    {
        return port;
    }

    public  void setPort( int port )
    {
        this.port = port;
    }

    public  int getMaxMessageSize()
    {
        return maxMessageSize;
    }

    public  void setMaxMessageSize( int maxMessageSize )
    {
        this.maxMessageSize = maxMessageSize;
    }

    public  ch.qos.logback.core.Layout<E> getLayout()
    {
        return layout;
    }

    public  void setLayout( ch.qos.logback.core.Layout<E> layout )
    {
        addWarn( "The layout of a SyslogAppender cannot be set directly. See also " + SYSLOG_LAYOUT_URL );
    }

    public  void stop()
    {
        if (sos != null) {
            sos.close();
        }
        super.stop();
    }

    public  java.lang.String getSuffixPattern()
    {
        return suffixPattern;
    }

    public  void setSuffixPattern( java.lang.String suffixPattern )
    {
        this.suffixPattern = suffixPattern;
    }

    public  java.nio.charset.Charset getCharset()
    {
        return charset;
    }

    public  void setCharset( java.nio.charset.Charset charset )
    {
        this.charset = charset;
    }

}

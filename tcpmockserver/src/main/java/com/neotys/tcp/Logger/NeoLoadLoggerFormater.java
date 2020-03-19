package com.neotys.tcp.Logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import static com.neotys.tcp.Logger.NeoLoadLogger.*;



public class NeoLoadLoggerFormater extends Formatter {

    private static final String SEVERE="SEVERE";
    private static final String FINE="FINE";

    private static final String FINEST="FINEST";
    private static final String WARNING="WARNING";

    String testid;
    String messageFormat="{" +
            "  \"logLevel\": \"%s\"," +
            "  \"message\": \"%s\"" +
            "}\n";

    public NeoLoadLoggerFormater(String testid) {
        this.testid = testid;
    }

    @Override
    public String format(LogRecord record) {
        String level;
        switch (record.getLevel().getName())
        {
            case INFO:
                level=INFO;
                break;
            case SEVERE:
                level=ERROR;
                break;
            case FINE:
                level= DEBUG;
                break;
            case FINEST:
                level= TRACE;
                break;
            case WARNING:
                level= WARNING;
                break;
            default: level= ERROR;
                break;
        }
        return String.format(messageFormat,level, record.getMillis() +" :" + record.getSourceClassName() +" - " +record.getSourceMethodName() + " - "+  record.getMessage());
    }
}

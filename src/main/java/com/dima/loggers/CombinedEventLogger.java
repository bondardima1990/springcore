package com.dima.loggers;

import com.dima.beans.Event;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;

public class CombinedEventLogger implements EventLogger{

    @Resource(name = "combinedLoggers")
    private Collection<EventLogger> loggers;

    public CombinedEventLogger(Collection<EventLogger> loggers) {
        this.loggers = loggers;
    }

    public Collection<EventLogger> getLoggers() {
        return Collections.unmodifiableCollection(loggers);
    }

    @Override
    public void logEvent(Event event) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.logEvent(event);
        }
    }
}

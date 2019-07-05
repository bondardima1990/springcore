package com.dima.loggers;

import com.dima.beans.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;

@Component
public class CombinedEventLogger extends FileEventLogger{

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

    @Value("#{'Combined ' + combinedLoggers.![name].toString()}")
    @Override
    protected void setName(String name) {
        this.name = name;
    }
}

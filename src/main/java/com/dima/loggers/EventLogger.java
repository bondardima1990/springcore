package com.dima.loggers;

import com.dima.beans.Event;

public interface EventLogger {

    void logEvent(Event event);

    String getName();

}

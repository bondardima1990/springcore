package com.dima.loggers;

import com.dima.beans.Event;

public class ConsoleEventLogger implements EventLogger {
    @Override
    public void logEvent(Event event){
        System.out.println(event.toString());
    }
}

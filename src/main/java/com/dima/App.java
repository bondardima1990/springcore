package com.dima;

import com.dima.beans.Client;
import com.dima.beans.Event;
import com.dima.beans.EventType;
import com.dima.loggers.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import java.util.Map;

public class App {

    @Autowired
    private Client client;

    @Value("#{ T(com.dima.beans.Event).isDay(8,17) ? "
            + "cacheFileEventLogger : consoleEventLogger }")
    private EventLogger defaultLogger;

    @Resource(name = "loggerMap")
    private Map<EventType, EventLogger> loggers;



    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultLogger = eventLogger;
        this.loggers = loggers;
    }

    public EventLogger getEventLogger() {
        return defaultLogger;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App) ctx.getBean("app");

        Event event = ctx.getBean(Event.class);
        app.logEvent(EventType.INFO, event,"Some event for 1");

        event = ctx.getBean(Event.class);
        app.logEvent(EventType.INFO, event,"One more event for 1");

        event = ctx.getBean(Event.class);
        app.logEvent(EventType.INFO, event,"And one more event for 1");

        event = ctx.getBean(Event.class);
        app.logEvent(EventType.ERROR, event,"Some event for 2");

        event = ctx.getBean(Event.class);
        app.logEvent(null, event, "Some event for 3");

        ctx.close();
    }

    public void logEvent(EventType eventType, Event event, String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);

        EventLogger logger = loggers.get(eventType);
        if (logger == null) {
            logger = defaultLogger;
        }

        logger.logEvent(event);
    }
}

package com.dima;

import com.dima.beans.Client;
import com.dima.beans.Event;
import com.dima.beans.EventType;
import com.dima.loggers.EventLogger;
import com.dima.spring.AppConfig;
import com.dima.spring.LoggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class App {

    @Autowired
    private Client client;

    @Value("#{ T(com.dima.beans.Event).isDay(8,17) ? "
            + "cacheFileEventLogger : consoleEventLogger }")
    private EventLogger defaultLogger;

    @Resource(name = "loggerMap")
    private Map<EventType, EventLogger> loggers;

    @Value("#{'Hello user ' + "
            + "( systemProperties['os.name'].contains('Windows') ? "
            + "systemEnvironment['USERNAME'] : systemEnvironment['USER'] ) + "
            + "'. Default logger is ' + app.defaultLogger.name }")
    private String startupMessage;

    public App() {

    }

    public App(Client client, EventLogger defaultLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggers = loggers;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class, LoggerConfig.class);
        ctx.scan("com.dima");
        ctx.refresh();

        App app = (App) ctx.getBean("app");

        System.out.println(app.startupMessage);

        Client client = ctx.getBean(Client.class);
        System.out.println("Client says: " + client.getGreeting());

        app.logEvents(ctx);

        ctx.close();
    }

    private void logEvents(ApplicationContext ctx) {

        Event event = ctx.getBean(Event.class);
        logEvent(EventType.INFO, event,"Some event for 1");

        event = ctx.getBean(Event.class);
        logEvent(EventType.INFO, event,"One more event for 1");

        event = ctx.getBean(Event.class);
        logEvent(EventType.INFO, event,"And one more event for 1");

        event = ctx.getBean(Event.class);
        logEvent(EventType.ERROR, event,"Some event for 2");

        event = ctx.getBean(Event.class);
        logEvent(null, event, "Some event for 3");
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

    public EventLogger getDefaultLogger() {
        return defaultLogger;
    }
}

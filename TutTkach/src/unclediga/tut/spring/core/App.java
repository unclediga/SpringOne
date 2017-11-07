package unclediga.tut.spring.core;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import unclediga.tut.spring.core.beans.Client;
import unclediga.tut.spring.core.beans.Event;
import unclediga.tut.spring.core.beans.EventType;
import unclediga.tut.spring.core.loggers.EventLogger;

import java.util.Map;

public class App {
    private Client client;
    private EventLogger defaultLogger;
    private final Map<EventType, EventLogger> loggers;

    public App(Client client, EventLogger eventLogger, Map<EventType,EventLogger> loggers) {
        super();
        this.client = client;
        this.defaultLogger = eventLogger;
        this.loggers = loggers;
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

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");

        Event event = (Event) ctx.getBean("event");
        app.logEvent(EventType.INFO, event,"Some message for user 1");

        event = (Event) ctx.getBean("event");
        app.logEvent(EventType.ERROR, event,"Some message for user 2");

        event = (Event) ctx.getBean("event");
        app.logEvent(null, event,"Some message for user 3");

        ctx.close();

    }

}

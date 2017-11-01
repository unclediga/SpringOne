package unclediga.tut.spring.core;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import unclediga.tut.spring.core.beans.Client;
import unclediga.tut.spring.core.beans.Event;
import unclediga.tut.spring.core.loggers.ConsoleEventLogger;
import unclediga.tut.spring.core.loggers.EventLogger;

public class App {
    private Client client;
    private EventLogger eventLogger;

    public App(Client client, EventLogger eventLogger) {
        super();
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public void logEvent(Event event, String msg) {

        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);
        eventLogger.logEvent(event);
    }

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");

        Event event = (Event) ctx.getBean("event");
        app.logEvent(event,"Some message for user 1");

        event = (Event) ctx.getBean("event");
        app.logEvent(event,"Some message for user 2");

    }

}

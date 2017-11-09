package unclediga.tut.spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import unclediga.tut.spring.core.beans.Client;
import unclediga.tut.spring.core.beans.Event;
import unclediga.tut.spring.core.beans.EventType;
import unclediga.tut.spring.core.loggers.EventLogger;
import unclediga.tut.spring.core.spring.AppConfig;
import unclediga.tut.spring.core.spring.LoggerConfig;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class App {
    @Autowired
    private Client client;

    @Value("#{T(unclediga.tut.spring.core.beans.Event).isDay(8,17) ? cacheFileEventLogger : consoleEventLogger}")
    private EventLogger defaultLogger;

    @Resource(name = "loggerMap")
    private Map<EventType, EventLogger> loggers;

    @Value("#{'Hello user ' + " +
            "(systemProperties['os.name'].contains('Windows') ? systemEnvironment['USERNAME'] : systemEnvironment['USER']) + " +
            "'. Default logger is ' + app.defaultLogger.name}")
    private String startupMessage;


    public EventLogger getDefaultLogger() {
        return defaultLogger;
    }

    public App(Client client, EventLogger eventLogger, Map<EventType,EventLogger> loggers) {
        super();
        this.client = client;
        this.defaultLogger = eventLogger;
        this.loggers = loggers;
    }


    public App(){

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

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class,LoggerConfig.class);
        ctx.scan("unclediga.tut.spring.core");
        ctx.refresh();

        App app = (App) ctx.getBean("app");

        System.out.println(app.startupMessage);

        Client client = ctx.getBean(Client.class);
        System.out.println("Client says: "+client.getGreeting());

        Event event = (Event) ctx.getBean("event");
        app.logEvent(EventType.INFO, event,"Some message for user 1");

        event = (Event) ctx.getBean("event");
        app.logEvent(EventType.ERROR, event,"Some message for user 2");

        event = (Event) ctx.getBean("event");
        app.logEvent(null, event,"Some message for user 3");

        ctx.close();

    }

}

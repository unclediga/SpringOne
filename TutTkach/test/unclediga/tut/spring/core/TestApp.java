package unclediga.tut.spring.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import unclediga.tut.spring.core.beans.Client;
import unclediga.tut.spring.core.beans.Event;
import unclediga.tut.spring.core.beans.EventType;
import unclediga.tut.spring.core.loggers.AbstractLogger;
import unclediga.tut.spring.core.loggers.EventLogger;

import static org.junit.Assert.*;

public class TestApp {

    private static final String MSG = "Hello";

    @Test
    public void testClientNameSubstitution() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        Client client = createClient();

        DummyLogger dummyLogger = new DummyLogger();


        App app = new App(client, dummyLogger, Collections.emptyMap());

        Event event = new Event(new Date(), DateFormat.getDateTimeInstance());
        invokeLogEvent(app, null, event, MSG + " " + client.getId());
        assertTrue(dummyLogger.getEvent().getMsg().contains(MSG));
        assertTrue(dummyLogger.getEvent().getMsg().contains(client.getFullName()));

        invokeLogEvent(app, null, event, MSG + " 0");
        assertTrue(dummyLogger.getEvent().getMsg().contains(MSG));
        assertFalse(dummyLogger.getEvent().getMsg().contains(client.getFullName()));
    }

    private Client createClient() {
        Client client = new Client();
        client.setId("25");
        client.setFullName("Bob");
        return client;
    }

    @Test
    public void testCorrectLoggerCall() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Client client = createClient();
        DummyLogger defaultLogger = new DummyLogger();
        DummyLogger infoLogger = new DummyLogger();


        App app = new App(client, defaultLogger, new HashMap<EventType, EventLogger>() {{
            put(EventType.INFO, infoLogger);
        }});

        Event event = new Event(new Date(), DateFormat.getDateTimeInstance());
        invokeLogEvent(app, null, event, MSG + " " + client.getId());
        assertNotNull(defaultLogger.getEvent());
        assertNull(infoLogger.getEvent());

        defaultLogger.setEvent(null);
        infoLogger.setEvent(null);

        invokeLogEvent(app, EventType.ERROR, event, MSG + " " + client.getId());
        assertNotNull(defaultLogger.getEvent());
        assertNull(infoLogger.getEvent());

        defaultLogger.setEvent(null);
        infoLogger.setEvent(null);

        invokeLogEvent(app, EventType.INFO, event, MSG + " " + client.getId());
        assertNull(defaultLogger.getEvent());
        assertNotNull(infoLogger.getEvent());
    }

    private void invokeLogEvent(App app, EventType eventType, Event event, String message) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Method method = app.getClass().getDeclaredMethod("logEvent", EventType.class ,Event.class, String.class);
        method.setAccessible(true);
        method.invoke(app, eventType, event, message);
    }

    private class DummyLogger extends AbstractLogger {

        private Event event;

        @Override
        public void logEvent(Event event) {
            this.event = event;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        public Event getEvent() {
            return event;
        }

        public void setEvent(Event event) {
            this.event = event;
        }
    }

    ;

}

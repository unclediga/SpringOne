package unclediga.tut.spring.core.loggers;

import unclediga.tut.spring.core.beans.Event;

public class ConsoleEventLogger implements EventLogger{

    @Override
    public void logEvent(Event event) {
        System.out.println(event.getMsg());
    }
}

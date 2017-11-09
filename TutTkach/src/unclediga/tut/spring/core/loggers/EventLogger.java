package unclediga.tut.spring.core.loggers;

import unclediga.tut.spring.core.beans.Event;

public interface EventLogger {

    public void logEvent(Event event);
    public String getName();
}

package unclediga.tut.spring.core.loggers;

import unclediga.tut.spring.core.beans.Event;
import unclediga.tut.spring.core.beans.EventType;

public interface EventLogger {

    public void logEvent(Event event);
}

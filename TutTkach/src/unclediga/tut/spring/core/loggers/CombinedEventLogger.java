package unclediga.tut.spring.core.loggers;

import unclediga.tut.spring.core.beans.Event;

import java.util.Collection;

public class CombinedEventLogger implements EventLogger{

    private final Collection<EventLogger> loggers;

    public CombinedEventLogger(Collection loggers) {
        super();
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
        for (EventLogger logger : loggers) {
            logger.logEvent(event);
        }
    }
}

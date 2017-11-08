package unclediga.tut.spring.core.loggers;

import org.springframework.stereotype.Component;
import unclediga.tut.spring.core.beans.Event;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class CombinedEventLogger implements EventLogger{

    @Resource(name = "combinedLoggers")
    private  Collection<EventLogger> loggers;

    @Override
    public void logEvent(Event event) {
        for (EventLogger logger : loggers) {
            logger.logEvent(event);
        }
    }
}

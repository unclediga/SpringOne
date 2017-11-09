package unclediga.tut.spring.core.loggers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import unclediga.tut.spring.core.beans.Event;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;

@Component
public class CombinedEventLogger extends AbstractLogger {

    @Resource(name = "combinedLoggers")
    private  Collection<EventLogger> loggers;

    @Override
    public void logEvent(Event event) {
        for (EventLogger logger : loggers) {
            logger.logEvent(event);
        }
    }

    public Collection<EventLogger> getLoggers(){
        return Collections.unmodifiableCollection(loggers);
    }

    @Value("#{'Combined '+ combinedEventLogger.loggers.![name].toString()}")
    @Override
    public void setName(String name) {
        this.name = name;
    }
}

package unclediga.tut.spring.core.loggers;

import org.springframework.stereotype.Component;
import unclediga.tut.spring.core.beans.Event;

@Component
public class ConsoleEventLogger implements EventLogger{

    @Override
    public void logEvent(Event event) {
        System.out.println(event.getMsg());
    }
}

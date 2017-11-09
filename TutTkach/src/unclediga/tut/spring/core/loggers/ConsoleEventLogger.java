package unclediga.tut.spring.core.loggers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import unclediga.tut.spring.core.beans.Event;

@Component
public class ConsoleEventLogger extends AbstractLogger{

    @Override
    public void logEvent(Event event) {
        System.out.println(event.getMsg());
    }

    @Value("Console logger")
    @Override
    public void setName(String name) {
        this.name = name;
    }
}

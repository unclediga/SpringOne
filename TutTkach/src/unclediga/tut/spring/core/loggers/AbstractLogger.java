package unclediga.tut.spring.core.loggers;

import unclediga.tut.spring.core.beans.Event;

public abstract class AbstractLogger implements EventLogger {
    protected String name;
    @Override
    public void logEvent(Event event) {

    }

    @Override
    public String getName() {
        return name;
    }

    public abstract void setName(String name);

}

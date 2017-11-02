package unclediga.tut.spring.core.loggers;

import org.apache.commons.io.FileUtils;
import unclediga.tut.spring.core.beans.Event;

import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {
    private int cacheSize;
    private List<Event> cache;


    public CacheFileEventLogger(String fileName, int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
        this.cache = new ArrayList<>(cacheSize);
    }

    @Override
    public void logEvent(Event event) {

        cache.add(event);
        if (cache.size() >= cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    private void writeEventsFromCache() {
        cache.stream().forEach(super::logEvent);
    }

    public void destroy() {

        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }

}


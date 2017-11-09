package unclediga.tut.spring.core.loggers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import unclediga.tut.spring.core.beans.Event;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class CacheFileEventLogger extends FileEventLogger {
    // default 5
//    @Value("${cache.size:5}")
    private int cacheSize = 5;

    private List<Event> cache;

    public CacheFileEventLogger() {
        super();
    }

    public CacheFileEventLogger(String fileName, int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
    }

    @Override
    public void logEvent(Event event) {

        cache.add(event);
        if (cache.size() >= cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    @Value("File logger with cache")
    @Override
    public void setName(String name) {
        this.name = name;
    }

    private void writeEventsFromCache() {
        cache.stream().forEach(super::logEvent);
    }

    @PostConstruct
    public void initCache(){
        this.cache = new ArrayList<>(cacheSize);
    }

    @PreDestroy
    public void destroy() {

        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }

}


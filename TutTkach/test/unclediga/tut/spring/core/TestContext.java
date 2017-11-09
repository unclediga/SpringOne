package unclediga.tut.spring.core;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import unclediga.tut.spring.core.beans.Client;
import unclediga.tut.spring.core.beans.Event;
import unclediga.tut.spring.core.loggers.CacheFileEventLogger;
import unclediga.tut.spring.core.loggers.CombinedEventLogger;
import unclediga.tut.spring.core.loggers.EventLogger;
import unclediga.tut.spring.core.loggers.FileEventLogger;
import unclediga.tut.spring.core.spring.AppConfig;
import unclediga.tut.spring.core.spring.LoggerConfig;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestContext {

    @Test
    public void testLoggersNames(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(LoggerConfig.class);
        ctx.scan(FileEventLogger.class.getPackage().getName());
        ctx.refresh();

        FileEventLogger fileEventLogger = ctx.getBean("fileEventLogger",FileEventLogger.class);
        CacheFileEventLogger cacheFileEventLogger = ctx.getBean("cacheFileEventLogger",CacheFileEventLogger.class);

        assertEquals(fileEventLogger.getName() +  " with cache",cacheFileEventLogger.getName());

        CombinedEventLogger combinedEventLogger = ctx.getBean(CombinedEventLogger.class);


        Collection<String> combiName = combinedEventLogger.getLoggers().stream().map(v -> v.getName()).collect(Collectors.toList());

        assertEquals(combinedEventLogger.getName(),"Combined " + combiName);

    }


    @Test
    public void testPropertyPlaceholderSystemOverride() {
        System.setProperty("id", "35");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        Client client = ctx.getBean(Client.class);
        ctx.close();
        assertEquals(client.getId(), "35");

    }

    @Test
    public void testFileEventLoggerEventsFileSysPropValue() throws IOException {
        File file = File.createTempFile("test", "FileEventLogger");
        file.deleteOnExit();

        assertFileEventLogger(file);
    }

    @Test
    public void testFileEventLoggerDefaultValue() throws IOException {
        File file = new File("events_log.txt");

        assertFileEventLogger(file);
    }

    private void assertFileEventLogger(File file) throws IOException {

        System.setProperty("events.file", file.getAbsolutePath());

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(LoggerConfig.class);
        ctx.scan(FileEventLogger.class.getPackage().getName());
        ctx.refresh();

        EventLogger logger = ctx.getBean("fileEventLogger", FileEventLogger.class);
        Event event = new Event();
        String uuid = UUID.randomUUID().toString();
        event.setMsg(uuid);
        logger.logEvent(event);
        ctx.close();

        String str = FileUtils.readFileToString(file);
        assertTrue(str.contains(uuid));
    }



}


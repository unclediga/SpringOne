package unclediga.tut.spring.core;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import unclediga.tut.spring.core.beans.Event;
import unclediga.tut.spring.core.loggers.CacheFileEventLogger;
import unclediga.tut.spring.core.loggers.FileEventLogger;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestCacheFileEventLogger {

    private File file;

    @Before
    public void createFile() throws IOException {
        file = File.createTempFile("test", "FileEventLogger");

    }

    @After
    public void dropFile() {
        file.delete();
    }


    private CacheFileEventLogger createAndInitCacheFileEventLogger() {
        CacheFileEventLogger logger = new CacheFileEventLogger(file.getAbsolutePath(),2);
        logger.init();
        logger.initCache();
        return logger;
    }


    @Test
    public void testLogEvent() throws IOException {
        CacheFileEventLogger logger = createAndInitCacheFileEventLogger();
        Event event = new Event(new Date(), DateFormat.getDateTimeInstance());
        event.setMsg("Hello");

        String content = FileUtils.readFileToString(file);
        assertTrue("init state : file empty",content.isEmpty());

        logger.logEvent(event);

        content = FileUtils.readFileToString(file);
        assertTrue("event was cached : file empty",content.isEmpty());

        logger.logEvent(event);

        content = FileUtils.readFileToString(file);
        assertFalse("cache are flashed to file : file not empty",content.isEmpty());

    }


    @Test
    public void testDestroy() throws IOException {
        CacheFileEventLogger logger = createAndInitCacheFileEventLogger();
        Event event = new Event(new Date(), DateFormat.getDateTimeInstance());
        event.setMsg("Hello");

        String content = FileUtils.readFileToString(file);
        assertTrue("init state : file empty",content.isEmpty());

        logger.logEvent(event);
        content = FileUtils.readFileToString(file);
        assertTrue("event was cached : file empty",content.isEmpty());

        logger.destroy();
        content = FileUtils.readFileToString(file);
        assertFalse("event flashed : file not empty",content.isEmpty());

    }


}

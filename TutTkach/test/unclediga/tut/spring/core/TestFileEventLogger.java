package unclediga.tut.spring.core;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import unclediga.tut.spring.core.beans.Event;
import unclediga.tut.spring.core.loggers.FileEventLogger;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestFileEventLogger {

    private File file;

    @Before
    public void createFile() throws IOException {
        file = File.createTempFile("test", "FileEventLogger");

    }

    @After
    public void dropFile() {
        file.delete();
    }

    @Test
    public void testInit() {
        FileEventLogger logger = new FileEventLogger(file.getAbsolutePath());
        logger.init();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitFail() {
        FileEventLogger logger = new FileEventLogger(file.getAbsolutePath());
        file.setReadOnly();
        logger.init();
    }

    @Test
    public void testLogEvent() throws IOException {
        FileEventLogger logger = new FileEventLogger(file.getAbsolutePath());
        logger.init();
        Event event = new Event(new Date(), DateFormat.getDateTimeInstance());
        event.setMsg("Hello");
        String content = FileUtils.readFileToString(file);
        assertTrue(content.isEmpty());
        logger.logEvent(event);
        content = FileUtils.readFileToString(file);
        assertFalse(content.isEmpty());
    }

}

package unclediga.tut.spring.core;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import unclediga.tut.spring.core.beans.Event;
import unclediga.tut.spring.core.loggers.ConsoleEventLogger;


public class TestConsoleEventLogger {

    private static final String MSG = "Message";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream stdout;

    @Before
    public void setUpStreams() {
        stdout = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(stdout);
    }

    @Test
    public void testLogEvent() {
        ConsoleEventLogger logger = new ConsoleEventLogger();
        Event event = new Event(new Date(), DateFormat.getDateTimeInstance());
        event.setMsg(MSG);
        logger.logEvent(event);
        Assert.assertTrue(outContent.toString().contains(MSG));
    }

}

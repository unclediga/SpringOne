package unclediga.tut.spring.core;

import org.junit.Test;
import unclediga.tut.spring.core.beans.Event;

import java.text.DateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;

public class TestEvent {

    @Test
    public void testToString(){
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        Event event = new Event(date, dateFormat);
        String s = event.toString();
        assertTrue(s.contains(dateFormat.format(date)));
    }
}

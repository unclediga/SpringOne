package unclediga.tut.spring.core.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class Event {
    private static AtomicInteger AUTOID = new AtomicInteger(0);
    private int id;
    private String msg;
    @Autowired
    @Qualifier("newDate")
    private Date date;
    @Autowired
    private DateFormat dateFormat;

    public Event() {
        this.id = AUTOID.getAndIncrement();
    }
    public Event(Date date,DateFormat dateFormat) {
        this();
        this.date = date;
        this.dateFormat = dateFormat;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", msg=" + msg + ", date=" + dateFormat.format(date) + "]";
    }

}

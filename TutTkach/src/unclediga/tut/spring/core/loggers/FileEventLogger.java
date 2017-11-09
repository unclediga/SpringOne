package unclediga.tut.spring.core.loggers;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import unclediga.tut.spring.core.beans.Event;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
@Component
public class FileEventLogger extends AbstractLogger{

    @Value("${events.file:out/events_log.txt}")
    private String fileName;
    private File file;

    public FileEventLogger() {
    }

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file,event.getMsg(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Value("File logger")
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @PostConstruct
    public void init() {
        file = new File(fileName);
        if (file.exists() && !file.canWrite()){
            throw new IllegalArgumentException("can not write to file " + fileName);
        }else if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IllegalArgumentException("can not create file " + fileName);
            }
        }
    }

}

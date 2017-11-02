package unclediga.tut.spring.core.loggers;

import org.apache.commons.io.FileUtils;
import unclediga.tut.spring.core.beans.Event;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger{
    private String fileName;
    private File file;

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

package com.dima.loggers;

import java.io.File;
import java.io.IOException;

import com.dima.beans.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class FileEventLogger implements EventLogger {

    private File file;

    @Value("${events.file:target/events_log.txt}")
    private String fileName;

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    @PostConstruct
    public void init() {
        file = new File(fileName);
        if (file.exists() && !file.canWrite()) {
            throw new IllegalArgumentException("Can't write to file " + fileName);
        } else if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IllegalArgumentException("Can't create file", e);
            }
        }
    }

    @Override
    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file, event.toString(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

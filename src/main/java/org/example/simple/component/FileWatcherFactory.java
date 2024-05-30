package org.example.simple.component;

import org.example.simple.events.FileWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileWatcherFactory {

    private final FileListener listener;
    private Runnable runnable;

    @Autowired
    public FileWatcherFactory(FileListener listener) {
        this.listener = listener;
    }

    @Value("${file.changed}")
    private String fileToChange;

    @EventListener(ApplicationReadyEvent.class)
    public void startFileListener(){
        FileWatcher fw = new FileWatcher(new File(fileToChange), listener);
        Thread t = new Thread(fw);
        t.start();
    }


}

package org.example.simple.component;

import org.springframework.stereotype.Component;

import java.nio.file.WatchEvent;

@Component
public class FileListener {

    public void onChanged(WatchEvent<?> event) {
        System.out.println("File updated...");
    }
}

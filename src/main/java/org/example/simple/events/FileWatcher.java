package org.example.simple.events;

import org.example.simple.component.FileListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileWatcher implements Runnable{

    private final File folder;
    private final FileListener listener;

    public FileWatcher(File folder, FileListener listener) {
        this.folder = folder;
        this.listener = listener;
    }


    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path path = Paths.get(folder.getAbsolutePath());
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);

            while (true) {
                pollEvents(watchService);
            }

        } catch (IOException | InterruptedException | ClosedWatchServiceException e) {
            throw new IllegalStateException(e);
        }
    }

    protected boolean pollEvents(WatchService watchService) throws InterruptedException {
        WatchKey key = watchService.take();
        for (WatchEvent<?> event : key.pollEvents()) {
            this.listener.onChanged(event);
        }
        return key.reset();
    }
}

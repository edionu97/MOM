package manager.impl;

import manager.IResourceManager;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class ResourceManager implements IResourceManager {

    private final String resourceFile;
    private final boolean openFile;

    public ResourceManager(final String resourceFile, final boolean open) {
        this.resourceFile = resourceFile;
        this.openFile = open;
    }

    @Override
    public void writeToResource(final StringBuilder content) {

        //open the file and save the content to it
        try {

            var url = getFilePath(resourceFile);
            if (url == null) {
                return;
            }

            var file = new File(url.toURI().getPath());
            try (var writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(content.toString());
            }

            if (!openFile) {
                return;
            }

            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearResource() {

        var url = getFilePath(resourceFile);
        if (url == null) {
            return;
        }

        //empty the file
        try (var writer = new FileWriter(url.toURI().getPath())) {
            writer.write("");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used in order to get the url of result file
     * @param filename: the name of the file
     * @return file's url or null if the file does not exist
     */
    private URL getFilePath(final String filename) {

        try {
            return Paths.get(filename).toUri().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

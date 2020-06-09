package manager;

public interface IResourceManager {

    /**
     * This method is responsible with writing the content into a specific file
     * @param content: the content that will be written
     */
    void writeToResource(final StringBuilder content);

    /**
     * Empty the file
     */
    void clearResource();
}

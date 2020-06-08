package services.invokers;

public interface IOperation {

    /**
     * Operation method for getting all the files by name
     * @param name: the name of the file (part of the name)
     * @return the method result
     */
    Object findFilesByName(final String name);

    /**
     * Operation method for getting all the files by their content
     * @param text: the content that must be present in the file (part of the content)
     * @return the method result
     */
    Object findFilesByContent(final String text);

    /**
     * Operation method for getting all the files by their binary content
     * @param bytes: an array of hex-bytes, separated through space
     * @return the method result
     */
    Object findFilesByBinary(final String bytes);

    /**
     * Operation method for getting all the duplicated files
     * @param payload: null
     * @return the method result
     */
    Object findDuplicateFiles(final String payload);

}

package services.files;

import java.util.List;

public interface IService {

    /**
     * Filters all the database, in order to seek files that start with a specific name
     * @param name: the name of the file (String)
     * @return a list of strings, representing the file path
     */
    List<String> findFilesByName(final String name);

    /**
     * Filter the database and seeks files that have in their content a text like @param text
     * @param text: the part of text that should be present in a file
     * @return a list of strings, representing the file path
     */
    List<String> findFilesByText(final String text);

    /**
     * Filter database and seek files that have in their content a binary
     * @param binary: the binary content
     * @return a list of strings, each string representing the path
     */
    List<String> findFilesByBinary(final int[] binary);

    /**
     * @return a list of of lists, representing, each list representing the paths of the same files
     */
    List<List<String>> findDuplicateFiles();




}

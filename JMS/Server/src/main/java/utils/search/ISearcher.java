package utils.search;

public interface ISearcher {

    /**
     * @param searchableString: the string in which we are searching a pattern
     * @param pattern: the pattern that is searched into that string
     * @return a number representing the number of apparitions of @param pattern in searchableString
     */
    int getNumberOfApparitions(
            final String searchableString, final String pattern);

    /**
     * Checks if the pattern is in the parent
     * @param parent: the main string
     * @param pattern: the pattern that is searched into the string
     * @return true if parent contains child or false otherwise
     */
    boolean isIn(final String pattern, final String parent);
}
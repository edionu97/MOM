package repo.repositories;

import java.util.List;
import java.util.function.Predicate;

public interface IRepository<T> {

    /**
     * @return a list with all entities from repository
     */
    List<T> getAll();

    /**
     * Adds a new object into database
     * @param object: the object that will be inserted
     * @throws Exception if something is wrong
     */
    void add(final T object) throws Exception;

    /**
     * @param filteringPredicate: the predicate that is used for data filtering
     * @return a list of filtered elements
     */
    List<T> filter(Predicate<T> filteringPredicate);
}

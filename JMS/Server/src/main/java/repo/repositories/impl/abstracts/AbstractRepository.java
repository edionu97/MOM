package repo.repositories.impl.abstracts;

import repo.repositories.IRepository;
import utils.persistence.HibernateUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractRepository<T> implements IRepository<T> {

    protected final Class<T> type;
    protected final HibernateUtils hibernateUtils;

    protected AbstractRepository(final Class<T> type) {
        this.hibernateUtils = utils.persistence.HibernateUtils.getInstance();
        this.type = type;
    }

    @Override
    public List<T> getAll() {
        //create the session
        try (var session = hibernateUtils.getSessionFactory().openSession()) {

            //create the query
            var query = session.getCriteriaBuilder().createQuery(type);
            query.select(query.from(type));

            //run the query and get the results
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public void add(final T object) throws Exception{

        //create the session
        try (var session = hibernateUtils.getSessionFactory().openSession()) {

            //start a new transaction
            var transaction = session.beginTransaction();

            //save the object or rollback if something is wrong, throw an exception
            try {
                session.save(object);
                transaction.commit();
            } catch (final Exception ex) {
                transaction.rollback();
                throw new Exception(ex);
            }
        }
    }

    @Override
    public List<T> filter(final Predicate<T> filteringPredicate) {
        return getAll()
                .stream()
                .filter(filteringPredicate)
                .collect(Collectors.toList());
    }
}

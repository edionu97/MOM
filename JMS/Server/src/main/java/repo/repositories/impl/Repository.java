package repo.repositories.impl;

import repo.repositories.impl.abstracts.AbstractRepository;

public class Repository<T> extends AbstractRepository<T> {

    public Repository(final Class<T> type) {
        super(type);
    }

}

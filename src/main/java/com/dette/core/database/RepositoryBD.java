package com.dette.core.database;

import com.dette.core.Repository;

public interface RepositoryBD<T> extends Repository<T> {

    T selectBy(String name);

}

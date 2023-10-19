package repository;

import models.User;

public interface CrudRepository<T> {
    void save(T entity);
    boolean findUser(String username, String password);
    User findByLogin(String email);

}

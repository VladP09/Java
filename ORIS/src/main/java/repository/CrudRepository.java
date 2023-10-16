package repository;

import java.util.List;

public interface CrudRepository<T> {
    void save(T entity);
    boolean findUser(String username, String password);
    boolean findByLogin(String email);

}

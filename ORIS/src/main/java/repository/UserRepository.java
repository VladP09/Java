package repository;

import models.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User> {
    List<User> findAll();

    String findIdByUUID(String uuid);
}

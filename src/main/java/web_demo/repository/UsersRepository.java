package web_demo.repository;

import org.springframework.data.repository.CrudRepository;
import web_demo.entity.Users;

public interface UsersRepository extends CrudRepository<Users,Long> {
    Users findByUsername(String username);
}

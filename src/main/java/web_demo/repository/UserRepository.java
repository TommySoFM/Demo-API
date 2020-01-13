package web_demo.repository;

import org.springframework.data.repository.CrudRepository;
import web_demo.entity.User;

public interface UserRepository extends CrudRepository<User,Integer> {

}

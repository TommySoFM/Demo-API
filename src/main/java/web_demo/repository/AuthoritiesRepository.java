package web_demo.repository;

import org.springframework.data.repository.CrudRepository;
import web_demo.entity.Authorities;

public interface AuthoritiesRepository extends CrudRepository <Authorities, Long> {
    Authorities findByUsername(String username);
}

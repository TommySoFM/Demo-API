package web_demo.repository;

import org.springframework.data.repository.CrudRepository;
import web_demo.entity.Authority;

public interface AuthorityRepository extends CrudRepository <Authority, Long> {
    Authority findByUsername(String username);
}

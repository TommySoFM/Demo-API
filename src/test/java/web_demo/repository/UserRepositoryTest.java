package web_demo.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web_demo.entity.User;
import web_demo.repository.UserRepository;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User tommy = new User("tommy","testPassword",1);

    @Before
    public void init() {
        userRepository.save(tommy);
    }

    @Test
    public void testFindByUsername() {
        Integer enabled = Integer.parseInt("1");

        User user = userRepository.findByUsername("tommy");
        assertEquals("tommy", user.getUsername());
        assertEquals("testPassword", user.getPassword());
        assertEquals(enabled, user.getEnabled());
    }
}

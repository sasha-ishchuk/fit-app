package com.sasha.fitapp;

import com.sasha.fitapp.model.Role;
import com.sasha.fitapp.model.User;
import com.sasha.fitapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateUser(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        User user = new User();
        user.setEmail("alexa.ishchuk@gmail.com");
        user.setPassword("sasha");
        user.setFirstName("Sasha");
        user.setLastName("Ishchuk");
        user.setRoles(List.of(role));

        User savedUser = userRepository.save(user);

        User existUser = testEntityManager.find(User.class, savedUser.getId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

}

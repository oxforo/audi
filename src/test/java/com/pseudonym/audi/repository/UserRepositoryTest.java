package com.pseudonym.audi.repository;

import com.pseudonym.audi.domain.User;
import com.pseudonym.audi.dto.UserSingUpDto;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanAll() {
        userRepository.deleteAll();
    }

    @Test
    void save_OK() {
        //given
        User user = new User();
        user.setId("user1");
        user.setEncryptedPw("!@#$LKJDSAF");
        user.setName("name");
        user.setEmail("user1@test.com");
        user.setPhoneNumber("010-1234-5678");

        //where
        User savedUser = userRepository.save(user);

        //then
        assertEquals(user, savedUser);
    }

    @Test
    void findById_OK() {
        //given
        User user = new User();
        user.setId("user1");
        user.setEncryptedPw("!@#$LKJDSAF");
        user.setName("name");
        user.setEmail("user1@test.com");
        user.setPhoneNumber("010-1234-5678");
        User savedUser = userRepository.save(user);

        //where
        User findUser = userRepository.findById(user.getId()).get();

        savedUser.equals(findUser);

        //then
        assertEquals(savedUser,findUser);
    }

    @Test
    void findById_unmatching_FAIL() {
        //given
        User user = new User();
        user.setId("user1");
        user.setEncryptedPw("!@#$LKJDSAF");
        user.setName("name");
        user.setEmail("user1@test.com");
        user.setPhoneNumber("010-1234-5678");
        userRepository.save(user);

        //where
        Optional<User> findUser = userRepository.findById("user2");

        //then
        assertEquals(Optional.empty(),findUser);
    }

    @Test
    void findByIdAndEmail_OK() {
        //given
        User user = new User();
        user.setId("user1");
        user.setEncryptedPw("!@#$LKJDSAF");
        user.setName("name");
        user.setEmail("user1@test.com");
        user.setPhoneNumber("010-1234-5678");
        User savedUser = userRepository.save(user);

        //where
        User findUser = userRepository.findByIdAndEmail(user.getId(), user.getEmail()).get();

        //then
        assertEquals(savedUser,findUser);
    }

    @Test
    void findByIdAndEmail_unmatching_FAIL() {
        //given
        User user = new User();
        user.setId("user1");
        user.setEncryptedPw("!@#$LKJDSAF");
        user.setName("name");
        user.setEmail("user1@test.com");
        user.setPhoneNumber("010-1234-5678");
        userRepository.save(user);

        //where
        Optional<User> findUser = userRepository.findByIdAndEmail("user2",user.getEmail());

        //then
        assertEquals(Optional.empty(),findUser);
    }

    @Test
    void findByNameAndEmail_OK() {
        //given
        User user = new User();
        user.setId("user1");
        user.setEncryptedPw("!@#$LKJDSAF");
        user.setName("name");
        user.setEmail("user1@test.com");
        user.setPhoneNumber("010-1234-5678");
        userRepository.save(user);

        //where
        User findUser = userRepository.findByNameAndEmail(user.getName(), user.getEmail()).get();

        //then
        assertEquals(user,findUser);
    }

    @Test
    void findByNameAndEmail_unmatching_FAIL() {
        //given
        User user = new User();
        user.setId("user1");
        user.setEncryptedPw("!@#$LKJDSAF");
        user.setName("name");
        user.setEmail("user1@test.com");
        user.setPhoneNumber("010-1234-5678");
        userRepository.save(user);

        //where
        Optional<User> findUser = userRepository.findByNameAndEmail(user.getName(),"users123@test.com");

        //then
        assertEquals(Optional.empty(),findUser);
    }
}
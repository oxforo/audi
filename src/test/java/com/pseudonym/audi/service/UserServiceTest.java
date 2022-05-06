package com.pseudonym.audi.service;

import com.pseudonym.audi.domain.User;
import com.pseudonym.audi.dto.LoginDto;
import com.pseudonym.audi.dto.UserIdFinderDto;
import com.pseudonym.audi.dto.UserPwFinderDto;
import com.pseudonym.audi.dto.UserSingUpDto;
import com.pseudonym.audi.exception.CustomException;
import com.pseudonym.audi.exception.ErrorCode;
import com.pseudonym.audi.repository.UserRepository;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    public void cleanAll() {
        userRepository.deleteAll();
    }

    @Test
    void join_OK() {
        //given
        UserSingUpDto userSingUpDto = new UserSingUpDto();
        userSingUpDto.setId("user1");
        userSingUpDto.setPw("1234");
        userSingUpDto.setName("name");
        userSingUpDto.setEmail("user1@test.com");
        userSingUpDto.setPhoneNumber("010-1234-5678");

        //where
        User joinedUser = userService.join(userSingUpDto);

        //then
        assertTrue(userSingUpDto.getId() == joinedUser.getId()
                && userSingUpDto.getName() == joinedUser.getName()
                && userSingUpDto.getEmail() == joinedUser.getEmail()
                && userSingUpDto.getPhoneNumber() == joinedUser.getPhoneNumber()
                && passwordEncoder.matches(userSingUpDto.getPw(), joinedUser.getEncryptedPw()));
    }

    @Test
    void join_FAIL() {
        //given
        UserSingUpDto userSingUpDto = new UserSingUpDto();
        userSingUpDto.setId("user1");
        userSingUpDto.setPw("1234");
        userSingUpDto.setName("name");
        userSingUpDto.setEmail("user1@test.com");
        userSingUpDto.setPhoneNumber("010-1234-5678");
        User joinedUser = userService.join(userSingUpDto);

        //where, then
        assertThatThrownBy(()->userService.join(userSingUpDto))
                .isInstanceOf(CustomException.class);
    }
    @Test
    void checkIdAndPw_OK() {
        //given
        UserSingUpDto userSingUpDto = new UserSingUpDto();
        userSingUpDto.setId("user1");
        userSingUpDto.setPw("1234");
        userSingUpDto.setName("name");
        userSingUpDto.setEmail("user1@test.com");
        userSingUpDto.setPhoneNumber("010-1234-5678");
        userService.join(userSingUpDto);

        LoginDto loginDto = new LoginDto();
        loginDto.setId("user1");
        loginDto.setPw("1234");

        //where
        boolean isCorrectIdAndPw = userService.checkIdAndEncryptedPw(loginDto);

        //then
        assertTrue(isCorrectIdAndPw);
    }

    @Test
    void checkIdAndPw_등록된사용자가_없을떄_FAIL() {
        //given
        LoginDto loginDto = new LoginDto();
        loginDto.setId("user1");
        loginDto.setPw("5678");

        //where, then
        assertThatThrownBy(()->userService.checkIdAndEncryptedPw(loginDto))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void checkIdAndPw_아이디가_틀렸을때_FAIL() {
        //given
        UserSingUpDto userSingUpDto = new UserSingUpDto();
        userSingUpDto.setId("user1");
        userSingUpDto.setPw("1234");
        userSingUpDto.setName("name");
        userSingUpDto.setEmail("user1@test.com");
        userSingUpDto.setPhoneNumber("010-1234-5678");
        userService.join(userSingUpDto);

        LoginDto loginDto = new LoginDto();
        loginDto.setId("user3");
        loginDto.setPw("1234");

        //where, then
        assertThatThrownBy(()->userService.checkIdAndEncryptedPw(loginDto))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void checkIdAndPw_패스워드가_틀렸을때_FAIL() {
        //given
        UserSingUpDto userSingUpDto = new UserSingUpDto();
        userSingUpDto.setId("user1");
        userSingUpDto.setPw("1234");
        userSingUpDto.setName("name");
        userSingUpDto.setEmail("user1@test.com");
        userSingUpDto.setPhoneNumber("010-1234-5678");
        userService.join(userSingUpDto);

        LoginDto loginDto = new LoginDto();
        loginDto.setId("user1");
        loginDto.setPw("5678");

        //where, then
        assertEquals(false, userService.checkIdAndEncryptedPw(loginDto));
//        assertThatThrownBy(()->userService.checkIdAndEncryptedPw(loginDto))
//                .isInstanceOf(CustomException.class);
    }

    @Test
    void getIdByNameAndEmail_OK() {
        //given
        UserSingUpDto userSingUpDto = new UserSingUpDto();
        userSingUpDto.setId("user1");
        userSingUpDto.setPw("1234");
        userSingUpDto.setName("name");
        userSingUpDto.setEmail("user1@test.com");
        userSingUpDto.setPhoneNumber("010-1234-5678");
        User user = userService.join(userSingUpDto);

        //when
        UserIdFinderDto userIdFinderDto = new UserIdFinderDto();
        userIdFinderDto.setEmail("user1@test.com");
        userIdFinderDto.setName("name");
        String id = userService.getIdByNameAndEmail(userIdFinderDto);

        //then
        assertEquals(user.getId(), id);
    }

    @Test
    void getIdByNameAndEmail_이메일_다를떄_FAIL() {
        //given
        UserSingUpDto userSingUpDto = new UserSingUpDto();
        userSingUpDto.setId("user1");
        userSingUpDto.setPw("1234");
        userSingUpDto.setName("name");
        userSingUpDto.setEmail("user1@test.com");
        userSingUpDto.setPhoneNumber("010-1234-5678");
        userService.join(userSingUpDto);

        //when
        UserIdFinderDto userIdFinderDto = new UserIdFinderDto();
        userIdFinderDto.setEmail("user123@test.com");
        userIdFinderDto.setName("name");

        //then
        assertThatThrownBy(() -> userService.getIdByNameAndEmail(userIdFinderDto))
                .isInstanceOf(CustomException.class);
    }

    @Test
    public void initPwByIdAndEmail_변경_OK() {
        //given
        UserSingUpDto userSingUpDto = new UserSingUpDto();
        userSingUpDto.setId("user1");
        userSingUpDto.setPw("1234");
        userSingUpDto.setName("name");
        userSingUpDto.setEmail("user1@test.com");
        userSingUpDto.setPhoneNumber("010-1234-5678");
        userService.join(userSingUpDto);

        //when
        UserPwFinderDto userPwFinderDto = new UserPwFinderDto();
        userPwFinderDto.setId("user1");
        userPwFinderDto.setEmail("user1@test.com");
        String firstNewPw = userService.initPwByIdAndEmail(userPwFinderDto);
        String secondNewPw = userService.initPwByIdAndEmail(userPwFinderDto);

        //then
        assertNotEquals(userSingUpDto.getPw(), firstNewPw);
        assertNotEquals(firstNewPw, secondNewPw);
    }

    @Test
    void initPwByIdAndEmail_아이디_다를떄_FAIL() {
        //given
        UserSingUpDto userSingUpDto = new UserSingUpDto();
        userSingUpDto.setId("user1");
        userSingUpDto.setPw("1234");
        userSingUpDto.setName("name");
        userSingUpDto.setEmail("user1@test.com");
        userSingUpDto.setPhoneNumber("010-1234-5678");
        userService.join(userSingUpDto);

        //when
        UserPwFinderDto userPwFinderDto = new UserPwFinderDto();
        userPwFinderDto.setId("user2");
        userPwFinderDto.setEmail("user1@test.com");

        //then
        assertThatThrownBy(() -> userService.initPwByIdAndEmail(userPwFinderDto))
                .isInstanceOf(CustomException.class);
    }
}
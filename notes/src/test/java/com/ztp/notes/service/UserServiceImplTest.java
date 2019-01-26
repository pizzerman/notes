package com.ztp.notes.service;

import com.ztp.notes.dao.UserRepository;
import com.ztp.notes.exception.NotesException;
import com.ztp.notes.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User testUser = new User("username", "password", true);
        Mockito.when(userRepository.findById(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.existsById(testUser.getUsername()))
                .thenReturn(true);
    }

    @Test
    public void whenValidUsername_thenUserShouldBeFound() {
        String username = "username";
        User found = userService.getUser(username);

        assertThat(found.getUsername()).isEqualTo(username);
    }

    @Test(expected = NotesException.class)
    public void whenUserIsNotFound_thenExceptionIsThrown() {
        String username = "wrongUsername";
        userService.getUser(username);
    }

    @Test(expected = NotesException.class)
    public void whenUsernameIsOccupied_thenExceptionIsThrown() {
        User user = new User("username", "password", true);
        userService.saveUser(user);
    }

    @Test(expected = NotesException.class)
    public void whenUsernameIsEmpty_thenExceptionIsThrown() {
        User user = new User("", "password", true);
        userService.saveUser(user);
    }

    @Test(expected = NotesException.class)
    public void whenUsernameHasWhitespaces_thenExceptionIsThrown() {
        User user = new User("user name", "password", true);
        userService.saveUser(user);
    }

    @Test(expected = NotesException.class)
    public void whenPasswordHasWhitespaces_thenExceptionIsThrown() {
        User user = new User("username", "pass word", true);
        userService.saveUser(user);
    }
}

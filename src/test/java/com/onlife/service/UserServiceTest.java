package com.onlife.service;

import com.onlife.domain.Role;
import com.onlife.domain.User;
import com.onlife.repositories.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUser() {
        User user = new User();
        user.setEmail("some@example.com");
        boolean isUserCreated = userService.addUser(user);

        Assert.assertTrue(isUserCreated);
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        Assert.assertNotNull(user.getActivationCode());
        verify(userRepository, times(1)).save(user);
        verify(mailSender, times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to Onlife"));
    }
    @Test
    public void addUserFailTest() {
        User testUser = new User();
        testUser.setUsername("Tester");
        doReturn(new User())
                .when(userRepository).findByUsername("Tester");

        boolean isUserCreated = userService.addUser(testUser);

        Assert.assertFalse(isUserCreated);
        verify(userRepository, times(0))
                .save(ArgumentMatchers.any(User.class));
        verify(mailSender, times(0))
                .send(ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void activateUserTest() {
        User testUser = new User();
        testUser.setActivationCode("pre-activate");
        doReturn(testUser)
                .when(userRepository).findByActivationCode("true-activate");

        boolean isUserActivated = userService.activateUser("true-activate");

        Assert.assertTrue(isUserActivated);
        Assert.assertNull(testUser.getActivationCode());
        verify(userRepository, times(1))
                .save(testUser);
    }

    @Test
    public void activateUserFailTest() {
        boolean isUserActivated = userService.activateUser("true-activate");

        Assert.assertFalse(isUserActivated);
        verify(userRepository, times(0))
                .save(ArgumentMatchers.any(User.class));
    }

    @Test
    public void updateUserRolesTest() {
        User testUser = new User();
        String testName = "testName";
        testUser.setRoles(new HashSet<Role>(Collections.singleton(Role.USER)));
        HashSet<Role> testRoles = new HashSet<>();
        testRoles.add(Role.USER);
        testRoles.add(Role.ADMIN);
        Map<String, String> roles = new HashMap<>();
        roles.put("USER", "-");
        roles.put("ADMIN", "-");

        userService.saveUser(testUser, testName, roles);

        Assert.assertEquals(testName, testUser.getUsername());
        Assert.assertEquals(testRoles, testUser.getRoles());
        verify(userRepository, times(1))
                .save(ArgumentMatchers.any(User.class));
    }

    @Test
    public void updateUserProfileSuccessTest() {
        User testUser = new User();
        testUser.setEmail("current@e.mail");
        testUser.setPassword("currentPass");
        String testEmail = "test@e.mail";
        String testPassword = "testPass";

        userService.updateProfile(testUser,testPassword,testEmail);

        Assert.assertNotNull(testUser.getActivationCode());
        Assert.assertEquals(passwordEncoder.encode(testPassword), testUser.getPassword());
        verify(userRepository, times(1))
                .save(ArgumentMatchers.any(User.class));
        verify(mailSender, times(1))
                .send(ArgumentMatchers.eq(testUser.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to Onlife"));
    }

}
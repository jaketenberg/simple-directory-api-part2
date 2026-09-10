package com.ping.simpledirectoryapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class UserServiceTest {

    @Test
    void testCreateUserAssignsIdAndStoresUser() {
        UserService userService = new UserService(new InMemoryUserStore());
        User user = new User();
        user.username = "jdoe";

        User created = userService.createUser("env-1", user);

        assertNotNull(created.id);
        assertEquals("jdoe", created.username);
    }

    @Test
    void testCreateUserDefaultsEnabledToTrueWhenNotProvided() {
        UserService userService = new UserService(new InMemoryUserStore());
        User user = new User();
        user.username = "jdoe";

        User created = userService.createUser("env-1", user);

        assertEquals(true, created.enabled);
    }

    @Test
    void testCreateUserPreservesExplicitEnabledValue() {
        UserService userService = new UserService(new InMemoryUserStore());
        User user = new User();
        user.username = "jdoe";
        user.enabled = false;

        User created = userService.createUser("env-1", user);

        assertEquals(false, created.enabled);
    }

    @Test
    void testGetUserReturnsStoredUser() {
        UserService userService = new UserService(new InMemoryUserStore());
        User user = new User();
        user.username = "jdoe";
        User created = userService.createUser("env-1", user);

        User fetched = userService.getUser("env-1", created.id);

        assertEquals(created.id, fetched.id);
    }
}

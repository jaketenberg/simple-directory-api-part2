package com.ping.simpledirectoryapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class InMemoryUserStoreTest {

    @Test
    void testGetReturnsCopyNotStoredReference() {
        InMemoryUserStore store = new InMemoryUserStore();
        User user = new User();
        user.id = "u-1";
        user.username = "jdoe";
        store.putUser("env-1", user);

        User fetched = store.getUser("env-1", "u-1");
        assertNotSame(user, fetched);

        fetched.username = "changed";
        assertEquals("jdoe", store.getUser("env-1", "u-1").username);
    }

    @Test
    void testPutStoresCopySoLaterCallerEditsDoNotAffectRecord() {
        InMemoryUserStore store = new InMemoryUserStore();
        User user = new User();
        user.id = "u-1";
        user.username = "jdoe";
        store.putUser("env-1", user);

        user.username = "changed";
        assertEquals("jdoe", store.getUser("env-1", "u-1").username);
    }

    @Test
    void testUpdateIsNotPersistedByMutationAlone() {
        InMemoryUserStore store = new InMemoryUserStore();
        User user = new User();
        user.id = "u-1";
        user.username = "jdoe";
        store.putUser("env-1", user);

        User fetched = store.getUser("env-1", "u-1");
        fetched.username = "mutated";
        store.putUser("env-1", fetched);

        assertEquals("mutated", store.getUser("env-1", "u-1").username);
        assertNull(store.getUser("env-1", "missing"));
    }
}

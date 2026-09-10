package com.ping.simpledirectoryapi;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public InMemoryUserStore store;

    public UserService(@Autowired InMemoryUserStore userStore) {
        this.store = userStore;
    }

    public User createUser(String environmentId, User user) {
        user.id = java.util.UUID.randomUUID().toString();
        if (user.enabled == null) {
            user.enabled = true;
        }
        LOGGER.info("creating user {} in environment {}", user.id, environmentId);
        store.putUser(environmentId, user);
        return user;
    }

    public User getUser(String environmentId, String userId) {
        LOGGER.info("fetching user {} in environment {}", userId, environmentId);
        return store.getUser(environmentId, userId);
    }

    /**
     * Applies a partial update: fields left null in the update keep their
     * existing values; fields present in the update overwrite the stored ones.
     * Returns null if the user does not exist in the environment.
     */
    public User updateUser(String environmentId, String userId, User update) {
        LOGGER.info("updating user {} in environment {}", userId, environmentId);
        User existing = store.getUser(environmentId, userId);
        if (existing == null) {
            return null;
        }
        if (update.username != null) {
            existing.username = update.username;
        }
        if (update.email != null) {
            existing.email = update.email;
        }
        if (update.givenName != null) {
            existing.givenName = update.givenName;
        }
        if (update.familyName != null) {
            existing.familyName = update.familyName;
        }
        if (update.streetAddress != null) {
            existing.streetAddress = update.streetAddress;
        }
        if (update.locality != null) {
            existing.locality = update.locality;
        }
        if (update.populationId != null) {
            existing.populationId = update.populationId;
        }
        if (update.enabled != null) {
            existing.enabled = update.enabled;
        }
        if (update.type != null) {
            existing.type = update.type;
        }
        store.putUser(environmentId, existing);
        return existing;
    }

    public void deleteUser(String environmentId, String userId) {
        LOGGER.info("deleting user {} in environment {}", userId, environmentId);
        store.removeUser(environmentId, userId);
    }
}

package com.ping.simpledirectoryapi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUserStore {

    public final Map<String, Map<String, User>> users = new ConcurrentHashMap<>();

    public User getUser(String environmentId, String userId) {
        Map<String, User> environmentUsers = users.get(environmentId);
        if (environmentUsers == null) {
            return null;
        }
        User user = environmentUsers.get(userId);
        return user == null ? null : new User(user);
    }

    public void putUser(String environmentId, User user) {
        users.computeIfAbsent(environmentId, k -> new ConcurrentHashMap<>()).put(user.id, new User(user));
    }

    public void removeUser(String environmentId, String userId) {
        Map<String, User> environmentUsers = users.get(environmentId);
        if (environmentUsers != null) {
            environmentUsers.remove(userId);
        }
    }
}

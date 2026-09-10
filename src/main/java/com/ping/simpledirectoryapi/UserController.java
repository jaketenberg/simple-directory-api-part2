package com.ping.simpledirectoryapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/environments/{environmentId}/users")
    public ResponseEntity<Object> createUser(@RequestAttribute(ClientTypeInterceptor.CLIENT_TYPE_ATTRIBUTE) ClientType clientType,
                                             @PathVariable String environmentId, @RequestBody User user) {
        User created = userService.createUser(environmentId, user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/environments/{environmentId}/users/{userId}")
    public ResponseEntity<Object> getUser(@RequestAttribute(ClientTypeInterceptor.CLIENT_TYPE_ATTRIBUTE) ClientType clientType,
                                          @PathVariable String environmentId, @PathVariable String userId) {
        User user = userService.getUser(environmentId, userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/environments/{environmentId}/users/{userId}")
    public ResponseEntity<Object> updateUser(@RequestAttribute(ClientTypeInterceptor.CLIENT_TYPE_ATTRIBUTE) ClientType clientType,
                                             @PathVariable String environmentId, @PathVariable String userId,
                                             @RequestBody User update) {
        User updated = userService.updateUser(environmentId, userId, update);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/environments/{environmentId}/users/{userId}")
    public ResponseEntity<Object> deleteUser(@RequestAttribute(ClientTypeInterceptor.CLIENT_TYPE_ATTRIBUTE) ClientType clientType,
                                             @PathVariable String environmentId, @PathVariable String userId) {
        userService.deleteUser(environmentId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

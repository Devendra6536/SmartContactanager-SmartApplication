package com.jwtauth.jwtauth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jwtauth.jwtauth.Model.User;

@Service
public class userService {

    private List<User> store = new ArrayList<>();

    public userService() {
        store.add(new User(UUID.randomUUID().toString(), "Devendra kumar", "devendrapal8682@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(), "Devendra Pal", "devendra8682@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(), "Avdhesh kumar", "abdhesh@gmail.com"));
    }

    public List<User> getAllUsers() {
        return this.store;
    }
}

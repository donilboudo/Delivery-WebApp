package com.ilboudofabrice.dao.interfaces;

import java.util.List;

import com.ilboudofabrice.domain.User;

/**
 * Created by filboudo on 2017-02-16.
 */
public interface UserDAO {
    void addUser(User user);

    void deleteUser(String userId);

    void updateLoginAndPassword(String userId, String login, String password);

    void updatePassword(String userId, String password);

    User findUserByCredentials(String userName, String password);

    List<User> getUsers();

    boolean isValidUser(String userName, String password);

    User findUserById(String userId);
}

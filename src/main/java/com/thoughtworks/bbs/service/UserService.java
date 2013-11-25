package com.thoughtworks.bbs.service;

import com.thoughtworks.bbs.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User get(long id);

    List<User> getAll();

    ServiceResult<User> save(User user);

    ServiceResult<User> update(User user);

    String changePassword(User user, String oldPassword, String newPassword, String confirmPassword);

    void delete(User user);

    User getByUsername(String username);
}

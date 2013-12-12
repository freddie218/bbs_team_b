package com.thoughtworks.bbs.service;

import com.thoughtworks.bbs.model.UserRole;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: st
 * Date: 13-12-9
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 */
public interface UserRoleService {
    List<Long> getAllNotAdmin();
    UserRole getUserRole();

    UserRole getByUserId(Long userId);

    void authoriseRoleName(UserRole userRole);
}

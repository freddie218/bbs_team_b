package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserRoleMapper {

    @Insert(
        "INSERT INTO user_roles(userid, rolename) " +
        "VALUES (#{userId}, #{roleName})"
    )
    void insert(UserRole userRole);

    @Select(
        "SELECT (userid, rolename) FROM user_roles " +
        "WHERE id=#{userId}"
    )
    UserRole get(Long userId);

    @Select(
            "SELECT userid as userId " +
                    "FROM user_roles " +
            "WHERE rolename=#{roleName}"
    )
    List<Long> getRegularUsersId(String roleName);


}

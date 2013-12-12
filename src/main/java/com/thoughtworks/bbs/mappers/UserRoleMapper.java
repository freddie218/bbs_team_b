package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserRoleMapper {

    @Insert(
        "INSERT INTO user_roles(userid, rolename) " +
        "VALUES (#{userId}, #{roleName})"
    )
    void insert(UserRole userRole);

    @Select(
            value = "SELECT userid as userId, rolename as roleName FROM user_roles " +
                    "WHERE userid=#{userId}"
    )
    UserRole get(Long userId);

    @Select(
            "SELECT userid as userId " +
                    "FROM user_roles " +
            "WHERE rolename=#{roleName}"
    )
    List<Long> getRegularUsersId(String roleName);
    @Update(
            value = "UPDATE user_roles " +
                    "SET  rolename=#{roleName} " +
                    "WHERE userid=#{userId}"
    )
    void authoriseUserRole(UserRole userRole);
}

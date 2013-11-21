package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {
    @Insert(
        "INSERT INTO users(username, password, enabled) " +
        "VALUES (#{userName}, #{passwordHash}, #{enabled})"
    )
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
    void insert(User user);

    @Update(
        "UPDATE users " +
        "SET username=#{userName}, password=#{passwordHash}, enabled=#{enabled} " +
        "WHERE id=#{id}"
        )
    void update(User user);

    @Select(
        "SELECT id, username, password as passwordHash, enabled " +
                "FROM users " +
                "WHERE username = #{username}"
    )
    User findByUsername(String username);
}

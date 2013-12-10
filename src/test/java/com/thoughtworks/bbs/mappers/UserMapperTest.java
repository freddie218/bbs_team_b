package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.util.UserBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserMapperTest extends MapperTestBase {
    UserMapper userMapper;
    User user;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userMapper = getSqlSession().getMapper(UserMapper.class);
        user = new User();
        user.setUserName("user");
        user.setPasswordHash("123456");
    }

    @Test
    public void shouldFindAllUsers(){
        int before = userMapper.getAll().size();
        User user1 = new UserBuilder().userName("user1").password("123456").build();
        User user2 = new UserBuilder().userName("user2").password("123456").build();

        userMapper.insert(user1);
        userMapper.insert(user2);
        assertThat(userMapper.getAll().size(),is(before + 2));
     }

    @Test
    public void shouldFindUserById() throws Exception {
        User user = new UserBuilder().userName("username").password("123456").build();
        userMapper.insert(user);
        assertThat(userMapper.findByUserId(user.getId()).getId(), is(user.getId()));
    }
}

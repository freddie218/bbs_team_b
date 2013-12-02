package com.thoughtworks.bbs.service.impl;

import com.thoughtworks.bbs.mappers.UserMapper;
import com.thoughtworks.bbs.mappers.UserRoleMapper;
import com.thoughtworks.bbs.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private UserServiceImpl userService;
    private User user;
    private UserMapper userMapper;
    private SqlSession session;
    private SqlSessionFactory sessionFactory;
    private UserRoleMapper userRoleMapper;
    private String oldPassWord;

    @Before
    public void setup(){
        session = mock(SqlSession.class);
        userMapper = mock(UserMapper.class);
        userRoleMapper = mock(UserRoleMapper.class);

        sessionFactory = mock(SqlSessionFactory.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.getMapper(UserMapper.class)).thenReturn(userMapper);
        when(session.getMapper(UserRoleMapper.class)).thenReturn(userRoleMapper);

        userService = new UserServiceImpl(sessionFactory);
        oldPassWord="wrong";
        user = new User();
        user.setUserName("user");
        user.setPasswordHash("password");
    }

    @Test
    public void shouldInsertUserWhenCreateNew(){
        userService.save(user);

        verify(userMapper).insert(user);
    }


    @Test
    public void shouldUpdateUserWhenUpdateUser(){
        userService.update(user);
    }

    @Test
    public void shouldReturnTrueWhenVerifyRightPassword(){
        String username = user.getUserName();
        String password = user.getPasswordHash();
        when(userMapper.findByUsername(username)).thenReturn(user);

        boolean expected = true;
        boolean result = userService.verifyPassword(username, password);
        assertThat(expected, equalTo(result));
    }

    @Test
    public void shouldReturnFalseWhenVerifyWrongPassword(){
        String username = user.getUserName();
        String password = user.getPasswordHash() + "_wrong";
        when(userMapper.findByUsername(username)).thenReturn(user);

        boolean request = false;
        boolean result = userService.verifyPassword(username, password);
        assertThat(result,equalTo(request));
    }

}

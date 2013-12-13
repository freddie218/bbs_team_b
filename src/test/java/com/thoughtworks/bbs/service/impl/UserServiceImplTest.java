package com.thoughtworks.bbs.service.impl;

import com.thoughtworks.bbs.mappers.UserMapper;
import com.thoughtworks.bbs.mappers.UserRoleMapper;
import com.thoughtworks.bbs.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
        verify(userMapper).update(user);
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

    @Test
    public void shouldGetAllUsers(){
        userService.getAll();
        verify(userMapper).getAll();
    }

    @Test
    public void shouldReturnFalseWhenVerifyRepeatedNewUsername() throws Exception {
        when(userMapper.findByUsername("username")).thenReturn(user);
        assertThat(userService.verifyUsername("username"), is(false));
    }

    @Test
    public void shouldReturnTrueWhenVerifyATotallyNewName() throws Exception {
        when(userMapper.findByUsername("username")).thenReturn(null);
        assertThat(userService.verifyUsername("username"), is(true));
    }

    @Test
    public void shouldReturnUserWhenFindUserById() throws Exception {
        when(userMapper.findByUserId(1L)).thenReturn(user);
        assertThat(userService.getByUserId(1L), is(user));
    }
    @Test
    public void shouldSetTrueWhenUsersNotAdmin(){
        List<User> userRegular = new LinkedList<User>();
        user.setId(1L);
        userRegular.add(user);
        List<Long> userNotAdmin = new LinkedList<Long>();
        Long userId = 1L;
        userNotAdmin.add(userId);
        userRegular=userService.setUsersIsRegular(userRegular,userNotAdmin);
        assertThat(userRegular.get(0).getIsRegular(), CoreMatchers.is(true));
    }

    @Test
    public void shouldSetFalseWhenUsersIsAdmin(){
        List<User> userAdmin = new LinkedList<User>();
        user.setId(1L);
        userAdmin.add(user);
        List<Long> userNotAdmin = new LinkedList<Long>();
        Long userId = 2L;
        userNotAdmin.add(userId);
        userAdmin=userService.setUsersIsRegular(userAdmin,userNotAdmin);
        assertThat(userAdmin.get(0).getIsRegular(), CoreMatchers.is(false));
    }
    @Test
    public void shouldsetUserwhenRegularUser(){
        List<User> userList= new LinkedList<User>();
        String expected = "User";
        User user_regular = new User();
        user_regular.setId(1L);
        userList.add(user_regular);
        Long id_user_regular = 1L;
        List<Long> usersNotAdmin = new LinkedList<Long>();
        usersNotAdmin.add(id_user_regular);
        userService.setUsersIsRegular(userList,usersNotAdmin);
        assertEquals(expected,user_regular.getUserRole());
    }
}

package com.thoughtworks.bbs.controller;

import org.springframework.ui.ModelMap;
import com.thoughtworks.bbs.mappers.UserMapper;
import com.thoughtworks.bbs.mappers.UserRoleMapper;
import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import com.thoughtworks.bbs.web.UserController;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: st
 * Date: 09-1-1
 * Time: 上午12:07
 * To change this template use File | Settings | File Templates.
 */
public class UserControllerTest {

    private UserServiceImpl userService;
    private User user;
    private UserMapper userMapper;
    private SqlSession session;
    private SqlSessionFactory sessionFactory;
    private UserRoleMapper userRoleMapper;
    private UserController userController;
    private ModelMap model;
    private Principal principal;


    @Before
    public void setup(){



        user = new User();
        user.setUserName("user");
        user.setPasswordHash("password");
        userService = mock(UserServiceImpl.class);
        when(userService.getByUsername("user")).thenReturn(user);
        model = mock(ModelMap.class);
        principal = mock(Principal.class);
        when(principal.getName()).thenReturn(user.getUserName());
        userController = new UserController();
        userController.srtUserService(userService);
    }

    @Test
    public void click_profile_should_return_profile(){
        ModelAndView modelAndView =
                userController.userProfile(model,principal);
        assertThat(modelAndView.getViewName(),is("user/profile"));

    }

}
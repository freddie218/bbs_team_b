package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.mappers.UserMapper;
import com.thoughtworks.bbs.mappers.UserRoleMapper;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: yang
 * Date: 11/16/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserControllerTest {

    private UserServiceImpl userService;
    private PostServiceImpl postService;
    private User user;

    private UserController userController;
    private ModelMap model;
    private Principal principal;

    @Before
    public void setup(){

        user = new User();
        user.setUserName("user");
        user.setPasswordHash("password");
        userService = mock(UserServiceImpl.class);
        postService = mock(PostServiceImpl.class);

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

    @Test
    public void profile_should_addAttribute(){
        userController.userProfile(model,principal);
        verify(model).addAttribute("posts",postService.findMainPostByAuthorName("user"));
    }



}

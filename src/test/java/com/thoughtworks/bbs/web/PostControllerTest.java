package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.UserService;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sun.security.acl.PrincipalImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostControllerTest {
    private PostService postservice;
    private UserService userService;
    private PostController postController;
    private HttpServletRequest request;
    private User user;

    private Principal principal;
    private Model model;
    private ModelAndView expected;
    private ModelAndView result;
    private RedirectView expected_rediret;
    private RedirectView result_rediret;
    @Before
    public void setup(){
        postservice = mock(PostServiceImpl.class);
        userService = mock(UserServiceImpl.class);
        request = mock(HttpServletRequest.class);

        user = new User();
        user.setUserName("name");
        user.setPasswordHash("123456");
        user.setId(0L);

        when(userService.getByUsername("name")).thenReturn(user);
        when(request.getParameter("parentId")).thenReturn("0");

        model = new ExtendedModelMap();
        principal = new PrincipalImpl("name");

        postController = new PostController(postservice,userService);
    }

    @Test
    public void shouldRedirectToHomeWhenCreatePostSuccess() throws IOException {

        when(request.getParameter("title")).thenReturn("title");
        when(request.getParameter("content")).thenReturn("content");

        result_rediret = postController.processCreate(request,principal);
        expected_rediret=new RedirectView("../");

        assertEquals("page should redirect to home",expected_rediret.getBeanName(),result_rediret.getBeanName());
    }



}

package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.UserService;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sun.security.acl.PrincipalImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
    private RedirectView expected_redirect;
    private RedirectView result_redirect;
    private ModelMap modelMap;
    private Long PostId;
    private Post post;
    @Before
    public void setup(){
        postservice = mock(PostServiceImpl.class);
        userService = mock(UserServiceImpl.class);
        request = mock(HttpServletRequest.class);
        modelMap = mock(ModelMap.class);
        user = new User();
        user.setUserName("name");
        user.setPasswordHash("123456");
        user.setId(0L);
        model = mock(Model.class);
        when(userService.getByUsername("name")).thenReturn(user);
        when(request.getParameter("parentId")).thenReturn("0");


        principal = new PrincipalImpl("name");

        postController = new PostController(postservice,userService);
        PostId=1L;
    }

    @Test
    public void shouldRedirectToHomeWhenCreatePostSuccess() throws IOException {

        when(request.getParameter("title")).thenReturn("title");
        when(request.getParameter("content")).thenReturn("content");

        result_redirect = postController.processCreate(request,principal);
        expected_redirect=new RedirectView("../");

        assertEquals("page should redirect to home",expected_redirect.getBeanName(),result_redirect.getBeanName());
    }

    @Test
    public void shouldAddMessageWhenPostContentBlank(){
        postController.postShow(request,principal,model,PostId);
        verify(model).addAttribute("posts", model.addAttribute("failed", "content is empty"));

    }

    @Test
    public void shouldReturnToTheCurrentPost(){
        when(request.getParameter("title")).thenReturn("title");
        when(request.getParameter("content")).thenReturn("content");
        String expected_page = "posts/show";
        String result_page = postController.postShow(request,principal,model,PostId);


        assertEquals("page should redirect to current page",expected_page,result_page);
    }

}

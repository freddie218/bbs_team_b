package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.ServiceResult;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

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
    private ModelMap model;
    private Principal principal;
    private HttpServletRequest request;

    private UserController userController;

    @Before
    public void setup(){
        userService = mock(UserServiceImpl.class);
        postService = mock(PostServiceImpl.class);
        user = new User();
        user.setUserName("user");
        user.setPasswordHash("password");
        model = mock(ModelMap.class);
        principal = mock(Principal.class);
        request = mock(HttpServletRequest.class);

        userController = new UserController();
        userController.srtUserService(userService);

        when(principal.getName()).thenReturn(user.getUserName());
        when(userService.getByUsername("user")).thenReturn(user);
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


    @Test
    public void shouldReturnChangePasswordPageWhenClickChangePassword(){
        assertThat(userController.changePassword(), equalTo("user/changePassword"));
    }

    @Test
    public void shouldReturnProfilePageAndUpdateUserWhenChangePasswordSuccess(){
        String expected = "user/profile";
        String result = null;

        when(userService.getByUsername(principal.getName())).thenReturn(user);
        when(userService.update(user)).thenReturn(new ServiceResult<User>(new HashMap<String, String>(),user));
        when(request.getParameter("username")).thenReturn(user.getUserName());
        when(request.getParameter("old password")).thenReturn(user.getPasswordHash());
        when(request.getParameter("new password")).thenReturn(user.getPasswordHash());
        when(request.getParameter("confirm password")).thenReturn(user.getPasswordHash());
        when(userService.verifyPassword(user.getUserName(), user.getPasswordHash())).thenReturn(true);
        result = userController.processChangePassword(model,request, principal).getViewName();

        verify(userService, times(1)).update(user);
        assertThat(expected, equalTo(result));
    }

    @Test
    public void shouldReturnProfilePageAndNotUpdateUserWhenChangePasswordFailed(){
        String expected = "user/profile";
        String result = null;

        when(userService.getByUsername(principal.getName())).thenReturn(user);
        when(userService.update(user)).thenReturn(new ServiceResult<User>(new HashMap<String, String>(),user));
        when(request.getParameter("username")).thenReturn(user.getUserName());
        when(request.getParameter("old password")).thenReturn(user.getPasswordHash());
        when(request.getParameter("new password")).thenReturn(user.getPasswordHash());
        when(request.getParameter("confirm password")).thenReturn(user.getPasswordHash()+"_wrong");
        when(userService.verifyPassword(user.getUserName(), user.getPasswordHash())).thenReturn(true);
        result = userController.processChangePassword(model,request, principal).getViewName();

        verify(userService, never()).update(user);
        assertThat(expected, equalTo(result));
    }
}

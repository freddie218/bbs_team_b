package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.Post;
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
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

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
        principal =  mock(Principal.class);
        request = mock(HttpServletRequest.class);

        userController = new UserController();
        userController.rstUserService(userService);
        userController.rstPostService(postService);

        when(principal.getName()).thenReturn(user.getUserName());
        when(userService.getByUsername("user")).thenReturn(user);
    }

    @Test
    public void click_userProfile_should_return_profile(){
        ModelAndView modelAndView =
                userController.userProfile(model,principal);
        assertThat(modelAndView.getViewName(),is("user/profile"));

    }

    @Test
    public void profile_should_addAttribute(){
        userController.userProfile(model,principal);
        verify(model).addAttribute("posts", postService.findMainPostByAuthorName("user"));

    }

    @Test
    public void click_authorName_should_return_profile(){
        String authorName = user.getUserName();
        Post post = new Post();
        ModelAndView modelAndView = userController.visitUserProfile(model,principal,authorName,post);
        assertThat(modelAndView.getViewName(),is("user/profile"));
    }

    @Test
    public void del_post_should_delete_post_and_return_redirect_string() {
        List<Post> postList = new LinkedList<Post>();
        for (int i = 0; i < 6; i++) {
            postList.add(0,new Post());
        }
        Post mainPost = new Post();
        mainPost.setAuthorName("M");
        when(postService.get(1L)).thenReturn(mainPost);
        when(principal.getName()).thenReturn("M");
        when(postService.findAllPostByMainPost(1L)).thenReturn(postList);

        String ret = userController.delPostId(1L, principal);
        for ( Post post:postList) {
            verify(postService).delete(post);
        }
        assertThat(ret, is("redirect:/user/profile"));
    }

    @Test
    public void shouldReturnChangePasswordPageWhenClickChangePassword(){
        assertThat(userController.changePassword(), equalTo("user/changePassword"));
    }

    @Test
    public void shouldReturnProfilePageAfterChangePassword() throws Exception {
        String expected = "user/profile";
        String result = null;
        result = userController.processChangePassword(model,request, principal).getViewName();
        assertThat(expected, equalTo(result));
    }

    @Test
    public void shouldUpdateUserWhenChangePasswordSuccess(){
        when(userService.getByUsername(principal.getName())).thenReturn(user);
        when(userService.update(user)).thenReturn(new ServiceResult<User>(new HashMap<String, String>(),user));
        when(request.getParameter("username")).thenReturn(user.getUserName());
        when(request.getParameter("old password")).thenReturn(user.getPasswordHash());
        when(request.getParameter("new password")).thenReturn(user.getPasswordHash());
        when(request.getParameter("confirm password")).thenReturn(user.getPasswordHash());
        when(userService.verifyPassword(user.getUserName(), user.getPasswordHash())).thenReturn(true);
        userController.processChangePassword(model, request, principal).getViewName();

        verify(userService, times(1)).update(user);
    }

    @Test
    public void shouldNotUpdateUserWhenChangePasswordFailed(){
        when(userService.getByUsername(principal.getName())).thenReturn(user);
        when(userService.update(user)).thenReturn(new ServiceResult<User>(new HashMap<String, String>(),user));
        when(request.getParameter("username")).thenReturn(user.getUserName());
        when(request.getParameter("old password")).thenReturn(user.getPasswordHash());
        when(request.getParameter("new password")).thenReturn(user.getPasswordHash());
        when(request.getParameter("confirm password")).thenReturn(user.getPasswordHash()+"_wrong");
        when(userService.verifyPassword(user.getUserName(), user.getPasswordHash())).thenReturn(true);
        userController.processChangePassword(model, request, principal).getViewName();

        verify(userService, never()).update(user);
    }

    @Test
    public void shouldGoToUsersWhenClickUsers(){
        ModelAndView modelAndView = userController.listUsers(model);
        assertThat(modelAndView.getViewName(),is("user/users"));
    }

    @Test
    public void shouldGoToUpdateProfilePageWhenClickUpdateProfile(){
        ModelAndView modelAndView = userController.updateProfile(model, principal);
        assertThat(modelAndView.getViewName(),is("user/updateProfile"));
    }

    @Test
    public void shouldGoToProfilePageWhenUpdateProfileFailed() throws Exception {
        ModelAndView modelAndView = userController.processUpdateProfile(model, request, principal);
        assertThat(modelAndView.getViewName(), is("user/updateProfile"));
    }

    @Test
    public void shouldGoToProfilePageWhenUpdateProfileSuccess() throws Exception{
        when(userService.getByUsername("username")).thenReturn(user);
        when(request.getParameter("new username")).thenReturn("username_new");
        when(userService.verifyUsername("username_new")).thenReturn(true);
        when(userService.getByUsername(principal.getName())).thenReturn(user);

        ModelAndView modelAndView = userController.processUpdateProfile(model, request, principal);
        assertThat(modelAndView.getViewName(), is("user/profile"));
    }

    @Test
    public void shouldUpdateProfileWhenNewUsernameValid() throws Exception {
        when(userService.getByUsername("username")).thenReturn(user);
        when(request.getParameter("new username")).thenReturn("username_new");
        when(userService.verifyUsername("username_new")).thenReturn(true);
        when(userService.getByUsername(principal.getName())).thenReturn(user);

        ModelAndView modelAndView = userController.processUpdateProfile(model, request, principal);

        verify(userService, times(1)).update(user);
    }

    @Test
    public void shouldNotUpdateProfileWhenNewUsernameInvalid() throws Exception {
        when(userService.getByUsername("username")).thenReturn(user);
        when(request.getParameter("new username")).thenReturn("username");
        when(userService.verifyUsername("username")).thenReturn(false);
        when(userService.getByUsername(principal.getName())).thenReturn(user);

        ModelAndView modelAndView = userController.processUpdateProfile(model, request, principal);
        verify(userService, never()).update(user);
    }

    @Test
    public void shouldNotUpdateProfileWhenNewUsernameIsBlank() throws Exception {
        when(userService.getByUsername("username")).thenReturn(user);
        when(request.getParameter("new username")).thenReturn("");
        when(userService.verifyUsername("")).thenReturn(true);
        when(userService.getByUsername(principal.getName())).thenReturn(user);

        ModelAndView modelAndView = userController.processUpdateProfile(model, request, principal);
        verify(userService, never()).update(user);
    }
}


package com.thoughtworks.bbs.web;
import com.thoughtworks.bbs.model.Like;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.impl.LikeServiceImpl;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.*;
import java.util.regex.Matcher;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 11/21/13
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeControllerTest {
    private PostServiceImpl postService;
    private UserServiceImpl userService;
    private LikeServiceImpl likeService;
    private Model model;
    private Post post;
    private Principal principal;
    private List<Post> postList;
    private List<Like> likeList;
    private HomeController homeController;
    private User user;

    @Before
    public void setup(){
        model = mock(Model.class);
        post = mock(Post.class);
        principal = mock(Principal.class);
        postList = new LinkedList<Post>();
        Post post = new Post().setPostId(1L);
        postList.add(post);
        likeList = new LinkedList<Like>();
        user=new User().setUserName("m").setPasswordHash("123456").setId(1L);
        postService = mock(PostServiceImpl.class);
        userService = mock(UserServiceImpl.class);
        likeService = mock(LikeServiceImpl.class);

        when(principal.toString()).thenReturn("m");
        when(principal.getName()).thenReturn("m");

        when(postService.findAllPost()).thenReturn(postList);
        when(userService.getByUsername("m")).thenReturn(user);
        when(likeService.doesUserLikePost(1L,1L)).thenReturn(true);

        homeController = new HomeController();
        homeController.rstPostService(postService);
        homeController.rstUserService(userService);
        homeController.rstLikeService(likeService);
    }

    @Test
    public void should_find_all_posts_and_return_home_when_home(){
        String ret = homeController.home(model, principal);
        verify(postService,times(1)).findAllPost();
        verify(likeService,times(postList.size())).doesUserLikePost(anyLong(), anyLong());
        assertThat(ret, is("home"));
    }
}

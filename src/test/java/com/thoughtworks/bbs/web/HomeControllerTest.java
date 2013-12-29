package com.thoughtworks.bbs.web;
import com.thoughtworks.bbs.model.Like;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.impl.LikeServiceImpl;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

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
    private Like like;
    private Principal principal;
    private List<Post> postList;
    private List<Like> likeList;
    private HomeController homeController;
    private User user;

    @Before
    public void setup(){
        model = mock(Model.class);
        principal = mock(Principal.class);
        postList = new LinkedList<Post>();
        post = new Post().setPostId(1L);
        postList.add(post);
        likeList = new LinkedList<Like>();
        like = new Like().setPostId(1L);
        likeList.add(like);
        user=new User().setUserName("m").setPasswordHash("123456").setId(1L);
        postService = mock(PostServiceImpl.class);
        userService = mock(UserServiceImpl.class);
        likeService = mock(LikeServiceImpl.class);

        when(principal.toString()).thenReturn("m");
        when(principal.getName()).thenReturn("m");

        when(postService.findAllPost()).thenReturn(postList);
        when(userService.getByUsername("m")).thenReturn(user);
        when(likeService.findLikeByUserId(1L)).thenReturn(likeList);

        homeController = new HomeController();
        homeController.rstPostService(postService);
        homeController.rstUserService(userService);
        homeController.rstLikeService(likeService);
    }

    @Test
    public void should_find_all_posts_and_return_home_when_home(){
        String ret = homeController.home(model, principal);
        verify(postService,times(1)).findAllPost();
        verify(likeService,times(1)).findLikeByUserId(1L);
        assertThat(ret, is("home"));
    }

    @Test
    public void should_write_a_lot_of_test_here_T_T() {
        //oh, no.
        //so many methods need to reload in HttpServletRequest
        //need an anonymous-inner-class to make it over
        //String ret = homeController.searchPost(new_HttpServletRequest_T_T,model,principal);
    }

    @Test
    public void shouldSortAllPostsByToppedWhenGoToHome() throws Exception {
        List<Post> postList = new LinkedList<Post>();
        when(postService.findAllPost()).thenReturn(postList);
        homeController.home(model, principal);
        verify(postService, times(1)).sortByTopped(postList);
    }
}

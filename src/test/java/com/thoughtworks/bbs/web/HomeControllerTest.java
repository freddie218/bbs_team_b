package com.thoughtworks.bbs.web;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.web.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import java.security.Principal;
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
    private Model model;
    private Post post;
    private Principal principal;
    private List<Post> list;
    private HomeController homeController;

    @Before
    public void setup(){
        model = mock(Model.class);
        post = mock(Post.class);
        principal = mock(Principal.class);
        list = mock(List.class);
        postService = mock(PostServiceImpl.class);
        when(model.addAttribute("posts", list)).thenReturn(model);
        when(principal.toString()).thenReturn("juntao");
        when(postService.findAllPost()).thenReturn(list);
        homeController = new HomeController();
        homeController.rstPostService(postService);
    }

    @Test
    public void should_find_all_posts_and_return_home_when_home(){
        String ret = homeController.getMethodForHome(model, post, principal);
        verify(postService,times(1)).findAllPost();
        assertThat(ret, is("home"));
    }

}

package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.Like;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.LikeService;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.UserService;
import com.thoughtworks.bbs.service.impl.LikeServiceImpl;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import sun.security.acl.PrincipalImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    private PostService postService;
    private UserService userService;
    private LikeService likeService;
    private PostController postController;
    private HttpServletRequest request;
    private User user;

    private Principal principal;
    private Model model;
    private RedirectAttributesModelMap redirectAttributesModelMap;
    private ModelAndView expected;
    private String result;
    private ModelMap modelMap;
    private Long PostId;
    private Post post;
    private Like like;
    @Before
    public void setup(){
        postService = mock(PostServiceImpl.class);
        userService = mock(UserServiceImpl.class);
        likeService = mock(LikeServiceImpl.class);
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

        postController = new PostController(postService,userService,likeService);
        PostId=1L;

        like=new Like().setPostId(1L).setParentId(0L).setUserId(1L);


        post = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(11).setLikedTimes(0);
    }

    @Test
    public void shouldRedirectToHomeWhenCreatePostSuccess() throws IOException {

        when(request.getParameter("title")).thenReturn("title");
        when(request.getParameter("content")).thenReturn("content");
        String expected_page = "redirect:/";
        String result_page = postController.processCreate(request,principal,redirectAttributesModelMap);
//        assertEquals("page should redirect to home",expected_redirect.getBeanName(),result_redirect.getBeanName());
        assertEquals("page should redirect to home",expected_page,result_page);
    }

//    @Test
//    public void shouldAddMessageWhenPostContentBlank(){
//        postController.postShow(request,principal,redirectAttributesModelMap,PostId);
//        verify(model).addAttribute("posts",redirectAttributesModelMap.addFlashAttribute("failed", "content is empty")) ;
//    }

    @Test
    public void shouldReturnToTheCurrentPost(){
        when(request.getParameter("title")).thenReturn("title");
        when(request.getParameter("content")).thenReturn("content");
        String expected_page = "redirect:1";
        String result_page = postController.postShow(request,principal,redirectAttributesModelMap,PostId);

        assertEquals("page should redirect to current page",expected_page,result_page);
    }

    @Test
    public void should_add_liked_times() {
        when(postService.get(1L)).thenReturn(post);
        postController.add1LikedTime(1L, "back", principal);
        verify(likeService).save(Matchers.<Like>anyObject());
        verify(postService).add1LikedTime(1L);
    }

    @Test
    public void shouldDeleteTheReplyPost() {
        Post aReplyPost = new Post().setPostId(2L).setParentId(1L);
        when(request.getParameter("deleteReplyPost")).thenReturn(String.valueOf(2L));
        when(postService.get(2L)).thenReturn(aReplyPost);
        String result = postController.processDeleteReplyPost(request,model);
        assertThat(result, is("redirect:1"));
    }

    @Test
    public void shouldTopPostWhenTopPost() throws Exception {
        Post post = new Post().setPostId(1L);
        postController.processTopPost(1L, principal);
        verify(postService).topPost(1L);
    }
}

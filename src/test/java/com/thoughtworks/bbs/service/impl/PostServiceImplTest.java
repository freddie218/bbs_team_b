package com.thoughtworks.bbs.service.impl;

import com.thoughtworks.bbs.mappers.PostMapper;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.util.PostBuilder;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {
    private PostServiceImpl postService;
    private PostBuilder postBuilder;
    private PostMapper mapper;
    private SqlSession session;
    private SqlSessionFactory sessionFactory;

    @Before
    public void setup(){
        mapper = mock(PostMapper.class);
        session = mock(SqlSession.class);
        sessionFactory = mock(SqlSessionFactory.class);

        when(session.getMapper(PostMapper.class)).thenReturn(mapper);
        when(sessionFactory.openSession()).thenReturn(session);

        postService = new PostServiceImpl(sessionFactory);

        postBuilder = new PostBuilder();
        postBuilder.author("juntao").title("Introduce to TDD").content("content");
    }

    @Test
    public void shouldInsertDataToPostServiceWhenNewPost(){
        Post post = postBuilder.build();
        postService.save(post);

        verify(mapper).insert(post);
    }

    @Test
    public void shouldUpdateDataToPostServiceWhenExistingPost(){
        Post post = postBuilder.id(1L).build();
        postService.save(post);

        verify(mapper).update(post);
    }
    @Test
    public void shouldDeleteDataFromPostServiceWhenDeletingPost(){
        Post post = postBuilder.id(1L).build();
        postService.delete(post);
        verify(mapper).delete(post);
    }

    @Test
    public void shouldGetMainPostByAuthorName() {
        String authorName = "juntao";
        List<Post> expectedPostList = new ArrayList();
        expectedPostList.add(new Post());
        when((mapper.findMainPostByAuthorName(authorName))).thenReturn(expectedPostList);

        List<Post> returnedPostList = postService.getMainPostByAuthorName(authorName);
        verify(mapper).findMainPostByAuthorName(authorName);
        assertThat(returnedPostList, is(expectedPostList));
    }

    @Test
    public void shouldGetAllPostsByAuthorName() throws Exception {
        String authorName = "yang";
        List<Post> expectedPostList = new ArrayList();
        when(mapper.findAllPostsByAuthorName(authorName)).thenReturn(expectedPostList);

        List<Post> returnedPostList = postService.getAllPostsByAuthorName(authorName);
        verify(mapper).findAllPostsByAuthorName(authorName);
        assertThat(expectedPostList, is(returnedPostList));
    }

    @Test
    public void shouldGetAllPostByMainPost() {
        Long id = 1L;

        List<Post> expectedPostList = new ArrayList();
        expectedPostList.add(new Post());
        when((mapper.findAllPostByMainPost(id))).thenReturn(expectedPostList);

        List<Post> returnedPostList = postService.findAllPostByMainPost(id);
        verify(mapper).findAllPostByMainPost(id);
        assertThat(returnedPostList, is(expectedPostList));
    }

    @Test
    public void shouldUpdatePostsAuthorWhenUpdatePostsAuthor() throws Exception {
        List<Post> postList = new LinkedList<Post>();
        postList.add(new Post().setAuthorName("user"));
        postList.add(new Post().setAuthorName("user"));

        when(mapper.findAllPostsByAuthorName("user")).thenReturn(postList);
        postService.updateAllPostsAuthorByUserName("user", "new_user");

        assertThat(postList.get(0).getAuthorName(), is("new_user"));
        assertThat(postList.get(1).getAuthorName(), is("new_user"));
    }

    @Test
    public void should_add_liked_times(){
        postService.add1LikedTime(1L);
        verify(mapper).add1LikedTime(1L);
    }

    @Test
    public void shouldFindPostByPostId() throws Exception {
        postService.findPostByPostId(1L);
        verify(mapper).get(1L);
    }

    @Test
    public void shouldUpdatePostWhenUpdatePost() throws Exception {
        Post post = new Post();
        postService.updatePost(post);
        verify(mapper).update(post);
    }

    @Test
    public void shouldTopPostWhenTopPost() throws Exception {
        Post post = new Post().setPostId(1L).setIsTopped(false);
        when(mapper.get(1L)).thenReturn(post);
        postService.topPost(1L);

        assertEquals(true, post.getIsTopped());
        verify(mapper).update(post);
    }
}


package com.thoughtworks.bbs.service.impl;

import com.thoughtworks.bbs.mappers.LikeMapper;
import com.thoughtworks.bbs.mappers.PostMapper;
import com.thoughtworks.bbs.model.Like;
import com.thoughtworks.bbs.util.LikeBuilder;
import com.thoughtworks.bbs.util.PostBuilder;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jesse on 12/11/13.
 */
public class LikeServiceImplTest {
    private LikeServiceImpl likeService;
    private LikeBuilder likeBuilder;
    private LikeMapper likeMapper;
    private SqlSession session;
    private SqlSessionFactory sessionFactory;

    @Before
    public void setup(){
        likeMapper = mock(LikeMapper.class);
        session = mock(SqlSession.class);
        sessionFactory = mock(SqlSessionFactory.class);

        when(session.getMapper(LikeMapper.class)).thenReturn(likeMapper);
        when(sessionFactory.openSession()).thenReturn(session);

        likeService = new LikeServiceImpl(sessionFactory);

        likeBuilder = new LikeBuilder();
        likeBuilder.postId(1L).parentId(0L).userId(1l);
    }

    @Test
    public void should_insert_data_when_new_like(){
        Like like = likeBuilder.build();
        likeService.save(like);
        verify(likeMapper).insert(like);
    }

    @Test
    public void should_use_mapper_when_find_by_post_id() {
        List<Like> returnedLikeList = likeService.findLikeByPostId(1L);
        verify(likeMapper).findLikeByPostId(1L);
    }

    @Test
    public void should_use_mapper_when_find_by_user_id() {
        List<Like> returnedLikeList = likeService.findLikeByUserId(1L);
        verify(likeMapper).findLikeByUserId(1L);
    }

    @Test
    public void should_return_true_when_user_likes() {

        List<Like> expectedList = new LinkedList<Like>();
        Like like1 = new Like().setPostId(4L).setParentId(0L).setUserId(1L);
        Like like2 = new Like().setPostId(4L).setParentId(0L).setUserId(2L);
        Like like3 = new Like().setPostId(4L).setParentId(0L).setUserId(4L);
        Like like4 = new Like().setPostId(4L).setParentId(0L).setUserId(3L);
        Like like5 = new Like().setPostId(4L).setParentId(0L).setUserId(5L);
        expectedList.add(like1);
        expectedList.add(like2);
        expectedList.add(like3);
        expectedList.add(like4);
        expectedList.add(like5);
        when(likeMapper.findLikeByPostId(4L)).thenReturn(expectedList);

        boolean ret = likeService.isUserLikesPost(1L, 4L);
        assertThat(true, is(ret));
    }

    @Test
    public void should_return_true_when_user_not_likes() {

        List<Like> expectedList = new LinkedList<Like>();
        Like like1 = new Like().setPostId(1L).setParentId(0L).setUserId(1L);
        Like like2 = new Like().setPostId(1L).setParentId(0L).setUserId(2L);
        Like like3 = new Like().setPostId(1L).setParentId(0L).setUserId(5L);
        Like like4 = new Like().setPostId(1L).setParentId(0L).setUserId(4L);
        Like like5 = new Like().setPostId(1L).setParentId(0L).setUserId(7L);
        expectedList.add(like1);
        expectedList.add(like2);
        expectedList.add(like3);
        expectedList.add(like4);
        expectedList.add(like5);
        when(likeMapper.findLikeByPostId(1L)).thenReturn(expectedList);

        boolean ret = likeService.isUserLikesPost(3L, 1L);
        assertThat(false, is(ret));
    }

    @Test
    public void should_return_true_when_is_favorite() {

        List<Like> expectedList = new LinkedList<Like>();
        Like like1 = new Like().setPostId(1L).setParentId(0L).setUserId(4L);
        Like like2 = new Like().setPostId(3L).setParentId(0L).setUserId(4L);
        Like like3 = new Like().setPostId(7L).setParentId(0L).setUserId(4L);
        Like like4 = new Like().setPostId(2L).setParentId(0L).setUserId(4L);
        Like like5 = new Like().setPostId(8L).setParentId(0L).setUserId(4L);
        expectedList.add(like1);
        expectedList.add(like2);
        expectedList.add(like3);
        expectedList.add(like4);
        expectedList.add(like5);
        when(likeMapper.findLikeByUserId(4L)).thenReturn(expectedList);

        boolean ret = likeService.isFavorite(4L,3L);
        assertThat(true, is(ret));
    }

    @Test
    public void should_return_true_when_not_favorite() {

        List<Like> expectedList = new LinkedList<Like>();
        Like like1 = new Like().setPostId(1L).setParentId(0L).setUserId(4L);
        Like like2 = new Like().setPostId(3L).setParentId(0L).setUserId(4L);
        Like like3 = new Like().setPostId(7L).setParentId(0L).setUserId(4L);
        Like like4 = new Like().setPostId(2L).setParentId(0L).setUserId(4L);
        Like like5 = new Like().setPostId(8L).setParentId(0L).setUserId(4L);
        expectedList.add(like1);
        expectedList.add(like2);
        expectedList.add(like3);
        expectedList.add(like4);
        expectedList.add(like5);
        when(likeMapper.findLikeByUserId(4L)).thenReturn(expectedList);

        boolean ret = likeService.isFavorite(4L,9L);
        assertThat(false, is(ret));
    }
}

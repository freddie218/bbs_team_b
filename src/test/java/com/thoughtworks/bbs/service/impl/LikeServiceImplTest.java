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
        verify(likeMapper).findAllLikeByPostId(1L);
    }

    @Test
    public void should_use_mapper_when_find_by_user_id() {
        List<Like> returnedLikeList = likeService.findLikeByUserId(1L);
        verify(likeMapper).findAllLikeByUserId(1L);
    }
}

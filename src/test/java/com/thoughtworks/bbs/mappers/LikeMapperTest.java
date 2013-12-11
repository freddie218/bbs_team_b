package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.Like;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by jesse on 12/10/13.
 */
public class LikeMapperTest extends MapperTestBase{
    private LikeMapper likeMapper;
    private Like like;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        likeMapper = getSqlSession().getMapper(LikeMapper.class);
        like = new Like().setPostId(1L).setParentId(0L).setUserId(1L);
    }

    @Test
    public void should_insert_new_like() {
        int before = likeMapper.getAll().size();

        likeMapper.insert(like);

        assertThat(likeMapper.getAll().size(), is(before + 1));
    }

    @Test
    public void should_find_all_like() {
        int before = likeMapper.getAll().size();
        Like like1 = new Like().setPostId(1L).setParentId(0L).setUserId(2L);
        Like like2 = new Like().setPostId(1L).setParentId(0L).setUserId(3L);
        Like like3 = new Like().setPostId(3L).setParentId(0L).setUserId(3L);
        likeMapper.insert(like1);
        likeMapper.insert(like2);
        likeMapper.insert(like3);

        assertThat(likeMapper.getAll().size(), is(before + 3));
    }

    @Test
    public void should_find_all_like_by_post_id() {
        int before = likeMapper.findLikeByPostId(1L).size();

        Like like1 = new Like().setPostId(1L).setParentId(0L).setUserId(2L);
        Like like2 = new Like().setPostId(1L).setParentId(0L).setUserId(3L);
        Like like3 = new Like().setPostId(3L).setParentId(0L).setUserId(3L);
        Like like4 = new Like().setPostId(2L).setParentId(0L).setUserId(3L);
        Like like5 = new Like().setPostId(1L).setParentId(0L).setUserId(3L);
        likeMapper.insert(like1);
        likeMapper.insert(like2);
        likeMapper.insert(like3);
        likeMapper.insert(like4);
        likeMapper.insert(like5);

        assertThat(likeMapper.findLikeByPostId(1L).size(), is(before + 3));
    }

    @Test
    public void should_find_all_like_by_user_id() {
        int before = likeMapper.findLikeByUserId(3L).size();

        Like like1 = new Like().setPostId(1L).setParentId(0L).setUserId(2L);
        Like like2 = new Like().setPostId(1L).setParentId(0L).setUserId(3L);
        Like like3 = new Like().setPostId(3L).setParentId(0L).setUserId(3L);
        Like like4 = new Like().setPostId(2L).setParentId(0L).setUserId(3L);
        Like like5 = new Like().setPostId(1L).setParentId(0L).setUserId(3L);
        likeMapper.insert(like1);
        likeMapper.insert(like2);
        likeMapper.insert(like3);
        likeMapper.insert(like4);
        likeMapper.insert(like5);

        assertThat(likeMapper.findLikeByUserId(3L).size(), is(before + 4));
    }
}
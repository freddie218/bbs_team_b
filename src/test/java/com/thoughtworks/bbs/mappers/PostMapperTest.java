package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.Post;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PostMapperTest extends MapperTestBase{
    PostMapper postMapper;
    Post post;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        postMapper = getSqlSession().getMapper(PostMapper.class);
        post = new Post().setAuthorName("juntao").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0);
    }

    @Test
    public void shouldInsertANewPost() {
        int before = postMapper.getAll().size();

        postMapper.insert(post);

        assertThat(postMapper.getAll().size(), is(before + 1));
    }

    @Test
    public void shouldFindMainPostByAuthorName() {
        int before = postMapper.findMainPostByAuthorName("longkai").size();

        Post post1 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(11);
        Post post2 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0);
        Post post3 = new Post().setAuthorName("juner").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(22);
        Post post4 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0);
        postMapper.insert(post1);
        postMapper.insert(post2);
        postMapper.insert(post3);
        postMapper.insert(post4);

        List<Post> resultList = postMapper.findMainPostByAuthorName("longkai");

        assertThat(resultList.size(), is(before + 2));
    }

    @Test
    public void shouldFindAllPostByAllName() {
        int before = postMapper.findAllPost().size();

        Post post1 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(11);
        Post post2 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0);
        Post post3 = new Post().setAuthorName("juner").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(22);
        Post post4 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0);
        postMapper.insert(post1);
        postMapper.insert(post2);
        postMapper.insert(post3);
        postMapper.insert(post4);

        List<Post> resultList = postMapper.findAllPost();

        assertThat(resultList.size(), is(before + 2));
    }


    @Test
    public void shouldFindAllPostByMainPost() {
        int before = postMapper.findAllPostByMainPost(3L).size();

        Post post1 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(3L);
        Post post2 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(2L);
        Post post3 = new Post().setAuthorName("juner").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(3L);
        Post post4 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(3L);
        postMapper.insert(post1);
        postMapper.insert(post2);
        postMapper.insert(post3);
        postMapper.insert(post4);

        List<Post> resultList = postMapper.findAllPostByMainPost(3L);

        assertThat(resultList.size(), is(before + 3));
    }
}

package com.thoughtworks.bbs.service.impl;

import com.thoughtworks.bbs.mappers.PostMapper;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.PostValidator;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.ServiceResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PostServiceImpl implements PostService {
    private SqlSessionFactory factory;

    public PostServiceImpl(SqlSessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Post get(Long postId) {
        SqlSession session = factory.openSession();
        Post post = null;
        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            post = postMapper.get(postId);
        } finally {
            session.close();
        }

        return post;
    }

    @Override
    public List<Post> findMainPostByAuthorName(String authorName) {
        SqlSession session = factory.openSession();
        List<Post> posts = new LinkedList<Post>();

        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            posts = postMapper.findMainPostByAuthorName(authorName);
        } finally {
            session.close();
        }

        return posts;
    }

    @Override
    public List<Post> findAllPostByAllName() {
        SqlSession session = factory.openSession();
        List<Post> posts = new LinkedList<Post>();

        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            posts = postMapper.findAllPostByAllName();
        } finally {
            session.close();
        }

        return posts;
    }

    @Override
    public ServiceResult<Post> save(Post post) {
        Map<String,String> errors = new PostValidator().validate(post);

        SqlSession session = factory.openSession();
        if (errors.isEmpty()) {
            try {
                PostMapper postMapper = session.getMapper(PostMapper.class);
                if (null == post.getPostId()) {
                    postMapper.insert(post);
                } else {
                    postMapper.update(post);
                }

                session.commit();
            } finally {
                session.close();
            }
        }

        return new ServiceResult<Post>(errors, post);
    }

    @Override
    public void delete(Post post) {
        SqlSession session = factory.openSession();

        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            postMapper.delete(post);
            session.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Post> findAllPostByMainPost(Long postId) {
        SqlSession session = factory.openSession();
        List<Post> posts = new LinkedList<Post>();

        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);

            posts = postMapper.findAllPostByMainPost(postId);
        } finally {
            session.close();
        }

        return posts;
    }
}

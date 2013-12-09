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
    public List<Post> findAllPost() {
        SqlSession session = factory.openSession();
        List<Post> posts = new LinkedList<Post>();

        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            posts = postMapper.findAllPost();
        } finally {
            session.close();
        }

        return posts;
    }

    @Override
    public int updatePostsAuthorByUserName(String user, String new_user) {
        SqlSession session = factory.openSession();
        int cnt = 0;

        try{
            PostMapper postMapper = session.getMapper(PostMapper.class);
            List<Post> postList = postMapper.findMainPostByAuthorName(user);
            for(Post post : postList){
                postMapper.update(post.setAuthorName(new_user));
                cnt ++;
            }
        }finally {
            session.close();
        }

        return cnt;
    }

    @Override
    public void add1LikedTime(Long postId) {
        SqlSession session = factory.openSession();
        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            postMapper.add1LikedTime(postId);
            session.commit();
        } finally {
            session.close();
        }
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

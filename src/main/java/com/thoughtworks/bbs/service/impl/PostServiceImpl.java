package com.thoughtworks.bbs.service.impl;

import com.thoughtworks.bbs.mappers.PostMapper;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.PostValidator;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.ServiceResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Date;
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
    public List<Post> getMainPostByAuthorName(String authorName) {
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
    public List<Post> getAllPostsByAuthorName(String authorName) {
        SqlSession session = factory.openSession();
        List<Post> posts = new LinkedList<Post>();

        try{
            PostMapper postMapper = session.getMapper(PostMapper.class);
            posts = postMapper.findAllPostsByAuthorName(authorName);
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
            posts = postMapper.findAllMainPost();
        } finally {
            session.close();
        }

        return posts;
    }
    @Override
    public List<Post> findRestrictedPost(String title, String content, String author, String timeLeft, String timeRight) {
        SqlSession session = factory.openSession();
        List<Post> posts = new LinkedList<Post>();
        int timeRightLength = timeRight.length();
        if(timeRightLength == 0)
            timeRight = "9999-12-31";
        else
            //merge the date "+1" bug
            timeRight = timeRight.substring(0, timeRightLength-1) + (char)(timeRight.charAt(timeRightLength-1) + 1);
        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            posts = postMapper.findRestrictedPost(filterlize(title), filterlize(content), filterlize(author),
                    timeLeft, timeRight);
        } finally {
            session.close();
        }

        return posts;
    }

    private String filterlize(String inStr) {
        return "%" + inStr + "%";
    }

    @Override
    public int updateAllPostsAuthorByUserName(String user, String new_user) {
        SqlSession session = factory.openSession();
        int cnt = 0;

        try{
            PostMapper postMapper = session.getMapper(PostMapper.class);
            List<Post> postList = postMapper.findAllPostsByAuthorName(user);
            for(Post post : postList){
                postMapper.update(post.setAuthorName(new_user));
                cnt ++;
            }
            session.commit();
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
    public void deleteAllPostsByMainPost(long postId) {
        List<Post> allPosts = findAllPostByMainPost(postId);
        for(Post p : allPosts)
        {
            delete(p);
        }
    }

    @Override
    public Post findPostByPostId(long postId) {
        Post post = new Post();
        SqlSession session = factory.openSession();
        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            post = postMapper.get(postId);
        } finally {
            session.close();
        }
        return post;
    }

    @Override
    public Post topPost(long postId) {
        Post post = findPostByPostId(postId);
        post.setIsTopped(true);
        updatePost(post);
        return post;
    }

    @Override
    public Post updatePost(Post post) {
        SqlSession session = factory.openSession();
        try {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            postMapper.update(post);
            session.commit();
        } finally {
            session.close();
        }
        return post;
    }

    @Override
    public List<Post> sortByTopped(List<Post> postList) {
        int i = 0;
        for(int j=0; j<postList.size(); j++){
            if(postList.get(j).getIsTopped()){
                postList.add(i++, postList.remove(j));
            }
        }

        return postList;
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

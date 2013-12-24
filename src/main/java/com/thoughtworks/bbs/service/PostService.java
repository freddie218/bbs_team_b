package com.thoughtworks.bbs.service;

import com.thoughtworks.bbs.model.Post;

import java.util.Date;
import java.util.List;

public interface PostService {

    Post get(Long postId);

    List<Post> getMainPostByAuthorName(String authorName);

    List<Post> getAllPostsByAuthorName(String authorName);

    ServiceResult<Post> save(Post post);

    void delete(Post post);

    List<Post> findAllPostByMainPost(Long postId);

    List<Post> findAllPost();

    List<Post> findRestrictedPost(String title, String content, String author, String timeLeft, String timeRight);

    int updateAllPostsAuthorByUserName(String user, String new_user);

    void add1LikedTime(Long l);

    void deleteAllPostsByMainPost(long postId);

    Post findPostByPostId(long postId);

    Post topPost(long postId);

    Post updatePost(Post post);
}

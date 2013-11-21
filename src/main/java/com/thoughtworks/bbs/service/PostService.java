package com.thoughtworks.bbs.service;

import com.thoughtworks.bbs.model.Post;

import java.util.List;

public interface PostService {

    Post get(Long postId);

    List<Post> findMainPostByAuthorName(String authorName);

    ServiceResult<Post> save(Post post);

    void delete(Post post);

    List<Post> findAllPostByMainPost(Long postId);

    List<Post> findAllPostByAllName();
}

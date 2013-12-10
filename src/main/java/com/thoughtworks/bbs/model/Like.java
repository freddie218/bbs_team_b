package com.thoughtworks.bbs.model;

/**
 * Created by jesse on 12/10/13.
 */
public class Like {
    private Long postId;
    private Long parentId;
    private Long userId;

    public Long getPostId() {
        return postId;
    }

    public Like setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public Like setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Like setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}

package com.thoughtworks.bbs.util;

import com.thoughtworks.bbs.model.Post;

import java.util.Date;

public class PostBuilder {
    private Post post;

    public PostBuilder() {
        post = new Post();
    }

    public PostBuilder author(String author) {
        post.setAuthorName(author);
        return this;
    }

    public PostBuilder title(String title) {
        post.setTitle(title);
        return this;
    }

    public PostBuilder parentId(Long parentId) {
        post.setParentId(parentId);
        return this;
    }

    public PostBuilder content(String content) {
        post.setContent(content);
        return this;
    }

    public PostBuilder createTime(Date createTime) {
        post.setCreateTime(createTime);
        return this;
    }

    public PostBuilder modifyTime(Date modifyTime) {
        post.setModifyTime(modifyTime);
        return this;
    }

    public PostBuilder creatorId(Long creatorId) {
        post.setCreatorId(creatorId);
        return this;
    }

    public PostBuilder modifierId(Long modifierId) {
        post.setModifierId(modifierId);
        return this;
    }

    public PostBuilder id(Long id) {
        post.setPostId(id);
        return this;
    }

    public Post build() {
        return post;
    }
}

package com.thoughtworks.bbs.util;

import com.thoughtworks.bbs.model.Like;

import java.util.Date;

public class LikeBuilder {
    private Like like;

    public LikeBuilder() {
        like = new Like();
    }

    public LikeBuilder postId(Long id) {
        like.setPostId(id);
        return this;
    }

    public LikeBuilder parentId(Long id) {
        like.setParentId(id);
        return this;
    }

    public LikeBuilder userId(Long id) {
        like.setUserId(id);
        return this;
    }

    public Like build() {
        return like;
    }
}

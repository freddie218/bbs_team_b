package com.thoughtworks.bbs.service;

import com.thoughtworks.bbs.model.Like;

import java.util.List;

/**
 * Created by jesse on 12/10/13.
 */
public interface LikeService {
    List<Like> findLikeByPostId(Long id);

    List<Like> findLikeByParentId(Long id);

    List<Like> findLikeByUserId(Long id);

    ServiceResult<Like> save(Like like);
}

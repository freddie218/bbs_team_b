package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.Like;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jesse on 12/10/13.
 */
public interface LikeMapper {
    @Select(
            "SELECT post_id as postId, parent_id as parentId, user_id as userId "+
            "from likes"
    )
    List<Like> getAll();

    @Insert(
            "INSERT INTO likes(post_id, parent_id, user_id) " +
            "VALUES (#{postId}, #{parentId}, #{userId})"
    )
    void insert(Like like);

    @Select(
            "SELECT post_id as postId, parent_id as parentId, user_id as userId " +
            "FROM likes " +
            "WHERE (post_id = #{postId} )"
    )
    List<Like> findAllLikeByPostId(Long postId);

    @Select(
            "SELECT post_id as postId, parent_id as parentId, user_id as userId " +
                    "FROM likes " +
                    "WHERE (user_id = #{userId} )"
    )
    List<Like> findAllLikeByUserId(Long userId);
}

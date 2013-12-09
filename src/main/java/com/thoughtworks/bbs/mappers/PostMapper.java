package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.Post;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PostMapper {

    @Insert(
        "INSERT INTO post(parent_id, author_name, title, content, create_time, modify_time, creator_id, modifier_id, liked_times) " +
        "VALUES (#{parentId}, #{authorName}, #{title}, #{content}, #{createTime}, #{modifyTime}, #{creatorId}, #{modifierId}, #{likedTimes})"
    )
    void insert(Post item);

    @Select(
            "SELECT id as postId, parent_id as parentId, author_name as authorName, title, content, create_time as createTime, " +
                    "modify_time as modifyTime, creator_id as creatorId, modifier_id as modifierId, liked_times as likedTimes " +
                    "FROM post " +
                    "WHERE id = #{postId}"
    )
    Post get(Long postId);

    @Select(
            "SELECT id as postId, parent_id as parentId, author_name as authorName, title, content, create_time as createTime, " +
                    "modify_time as modifyTime, creator_id as creatorId, modifier_id as modifierId, liked_times as likedTimes " +
                    "FROM post "
    )
    List<Post> getAll();

    @Select(
            "SELECT id as postId, parent_id as parentId, author_name as authorName, title, content, create_time as createTime, " +
                    "modify_time as modifyTime, creator_id as creatorId, modifier_id as modifierId, liked_times as likedTimes " +
                    "FROM post " +
                    "WHERE author_name = #{authorName} and parent_id = 0 " +
                    "ORDER BY create_time desc"
    )
    List<Post> findMainPostByAuthorName(String authorName);

    @Select(
            "SELECT id as postId, parent_id as parentId, author_name as authorName, title, content, create_time as createTime, " +
                    "modify_time as modifyTime, creator_id as creatorId, modifier_id as modifierId, liked_times as likedTimes " +
                    "FROM post " +
                    "WHERE parent_id = 0 " +
                    "ORDER BY create_time desc"
    )
    List<Post> findAllPost();

    @Update(
        "UPDATE post " +
        "SET parent_id=#{parentId}, author_name=#{authorName}, title=#{title}, content=#{content}, create_time=#{createTime}," +
                " modify_time=#{modifyTime}, creator_id=#{creatorId}, modifier_id=#{modifierId}, liked_times=#{likedTimes} " +
        "WHERE id=#{postId}"
    )
    void update(Post post);

    @Delete(
        "DELETE FROM post WHERE id=#{postId}"
    )
    void delete(Post post);

    @Delete(
            "DELETE FROM post "
    )
    void deleteAll();

    @Select(
            "SELECT id as postId, parent_id as parentId, author_name as authorName, title, content, create_time as createTime, " +
                    "modify_time as modifyTime, creator_id as creatorId, modifier_id as modifierId, liked_times as likedTimes " +
                    "FROM post " +
                    "WHERE (id = #{postId} and parent_id = 0)" +
                    "OR parent_id = #{postId} " +
                    "ORDER BY id asc"
    )
    List<Post> findAllPostByMainPost(Long postId);
}

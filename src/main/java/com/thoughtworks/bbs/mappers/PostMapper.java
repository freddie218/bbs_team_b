package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PostMapper {

    @Insert(
        "INSERT INTO post(parent_id, author_name, title, content, create_time, modify_time, creator_id, modifier_id, liked_times, is_topped) " +
        "VALUES (#{parentId}, #{authorName}, #{title}, #{content}, #{createTime}, #{modifyTime}, #{creatorId}, #{modifierId}, #{likedTimes}, #{isTopped})"
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
    List<Post> findAllPost();

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
                    "WHERE author_name = #{authorName} " +
                    "ORDER BY create_time desc"
    )
    List<Post> findAllPostsByAuthorName(String authorName);


    @Select(
            "SELECT id as postId, parent_id as parentId, author_name as authorName, title, content, create_time as createTime, " +
                    "modify_time as modifyTime, creator_id as creatorId, modifier_id as modifierId, liked_times as likedTimes, is_topped as isTopped " +
                    "FROM post " +
                    "WHERE parent_id = 0 " +
                    "ORDER BY create_time desc"
    )
    List<Post> findAllMainPost();

    @Select(
            "SELECT id as postId, parent_id as parentId, author_name as authorName, title, content, create_time as createTime, " +
                    "modify_time as modifyTime, creator_id as creatorId, modifier_id as modifierId, liked_times as likedTimes " +
                    "FROM post " +
                    "WHERE (parent_id = 0 " +
                    "and title LIKE #{titleFilter} and content LIKE #{contentFilter} and author_name LIKE #{authorFilter}) " +
                    "and create_time >= #{timeLeft} and create_time <= #{timeRight} " +
                    "ORDER BY create_time desc"
    )
    List<Post> findRestrictedPost(@Param(value="titleFilter")String titleFilter,
                                  @Param(value="contentFilter")String contentFilter,
                                  @Param(value="authorFilter")String authorFilter,
                                  @Param(value="timeLeft")String timeLeft,
                                  @Param(value="timeRight")String timeRight);

    @Update(
        "UPDATE post " +
        "SET parent_id=#{parentId}, author_name=#{authorName}, title=#{title}, content=#{content}, create_time=#{createTime}," +
                " modify_time=#{modifyTime}, creator_id=#{creatorId}, modifier_id=#{modifierId}, liked_times=#{likedTimes}, is_topped=#{isTopped} " +
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



    @Update(
            "UPDATE post " +
            "SET liked_times=liked_times+1 " +
            "WHERE id=#{postId}"
    )
    void add1LikedTime(Long postId);

}

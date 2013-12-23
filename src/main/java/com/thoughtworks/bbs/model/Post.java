package com.thoughtworks.bbs.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private Long postId;
    private Long parentId;
    private String authorName;
    private String title;
    private String content;
    private Date createTime;
    private Date modifyTime;
    private Long creatorId;
    private Long modifierId;
    private String createTimeString;
    private Long likedTimes;
    private boolean isTopped;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Post setPostId(long postId) {
        this.postId = postId;
        return this;
    }

    public Post setParentId(long parentId) {
        this.parentId = parentId;
        return this;
    }

    public Post setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public Post setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createTimeString = dateFormat.format(createTime);
        return this;
    }

    public Post setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public Post setCreatorId(long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Post setModifierId(long modifierId) {
        this.modifierId = modifierId;
        return this;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public Post setLikedTimes(long likedTimes)  {
        this.likedTimes=likedTimes;
        return this;
    }
    public Long getPostId() {
        return postId;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public Long getLikedTimes(){
        return likedTimes;
    }

    public boolean getIsTopped(){
        return isTopped;
    }

    public Post setIsTopped(boolean isTopped){
        this.isTopped = isTopped;
        return this;
    }


}

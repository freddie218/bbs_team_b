package com.thoughtworks.bbs.service.impl;

import com.thoughtworks.bbs.mappers.LikeMapper;
import com.thoughtworks.bbs.model.Like;
import com.thoughtworks.bbs.model.LikeValidator;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.service.LikeService;
import com.thoughtworks.bbs.service.ServiceResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by jesse on 12/11/13.
 */
public class LikeServiceImpl implements LikeService {
    private SqlSessionFactory factory;
    public LikeServiceImpl(SqlSessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Like> findLikeByPostId(Long likeId) {
        SqlSession session = factory.openSession();
        List<Like> likes = new LinkedList<Like>();

        try {
            LikeMapper likeMapper = session.getMapper(LikeMapper.class);
            likes = likeMapper.findAllLikeByPostId(likeId);
        } finally {
            session.close();
        }

        return likes;
    }

    @Override
    public List<Like> findLikeByParentId(Long likeId) {
        return null;
    }

    @Override
    public List<Like> findLikeByUserId(Long likeId) {
        SqlSession session = factory.openSession();
        List<Like> likes = new LinkedList<Like>();

        try {
            LikeMapper likeMapper = session.getMapper(LikeMapper.class);
            likes = likeMapper.findAllLikeByUserId(likeId);
        } finally {
            session.close();
        }

        return likes;
    }

    @Override
    public ServiceResult<Like> save(Like like) {
        Map<String,String> errors = new LikeValidator().validate(like);

        SqlSession session = factory.openSession();
        if (errors.isEmpty()) {
            try {
                LikeMapper likeMapper = session.getMapper(LikeMapper.class);

                likeMapper.insert(like);

                session.commit();
            } finally {
                session.close();
            }
        }

        return new ServiceResult<Like>(errors, like);
    }
}

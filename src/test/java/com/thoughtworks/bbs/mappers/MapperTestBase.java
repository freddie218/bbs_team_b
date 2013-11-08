package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;

public abstract class MapperTestBase {
    private SqlSession sqlSession;

    @Before
    public void setUp() throws Exception {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    }

    @After
    public void tearDown() throws Exception {
        sqlSession.rollback();
        sqlSession.close();
    }

    protected SqlSession getSqlSession() {
        return sqlSession;
    }

}

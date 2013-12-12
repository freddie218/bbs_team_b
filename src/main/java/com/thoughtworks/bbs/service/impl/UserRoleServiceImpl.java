package com.thoughtworks.bbs.service.impl;

import com.thoughtworks.bbs.mappers.UserRoleMapper;
import com.thoughtworks.bbs.model.UserRole;
import com.thoughtworks.bbs.service.UserRoleService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: st
 * Date: 13-12-9
 * Time: 上午10:33
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleServiceImpl implements UserRoleService {
    private SqlSessionFactory factory;

    public UserRoleServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.factory = sqlSessionFactory;

    }

    @Override
    public List<Long> getAllNotAdmin(){
        SqlSession session = factory.openSession();
        String userRole = "ROLE_REGULAR";
        List<Long> userList= new LinkedList<Long>();

        try {
            UserRoleMapper mapper = session.getMapper(UserRoleMapper.class);
            userList = mapper.getRegularUsersId(userRole);
        }finally {
            session.close();
        }

        return userList;
    }
    @Override
    public UserRole getUserRole() {
        UserRole user_role = new UserRole();
        return user_role;
    }
    @Override
    public UserRole getByUserId(Long userId){
        SqlSession session = factory.openSession();
        UserRole userRole = new UserRole();
        try{
            UserRoleMapper mapper = session.getMapper(UserRoleMapper.class);
            userRole = mapper.get(userId);
            session.commit();
        }finally {
            session.close();
        }
        return userRole;
    }
    @Override
    public void authoriseRoleName(UserRole userRole){
         SqlSession session = factory.openSession();
        try{
            UserRoleMapper mapper = session.getMapper(UserRoleMapper.class);
            mapper.authoriseUserRole(userRole);
            session.commit();
        } finally {
            session.close();
        }
    }
}

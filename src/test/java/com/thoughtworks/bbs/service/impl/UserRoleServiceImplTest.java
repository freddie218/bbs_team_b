package com.thoughtworks.bbs.service.impl;

import com.thoughtworks.bbs.mappers.UserRoleMapper;
import com.thoughtworks.bbs.model.UserRole;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: st
 * Date: 13-12-9
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleServiceImplTest {
    private SqlSession session;
    private SqlSessionFactory sessionFactory;
    private UserRoleServiceImpl userRoleService;
    private UserRoleMapper mapper;
    private UserRole userRole_admin;
    private UserRole userRole_regular1;
    private UserRole userRole_regular2;

    @Before
    public void setup(){
        userRole_admin = new UserRole();
        userRole_regular1 = new UserRole();
        userRole_regular2 = new UserRole();

        userRole_admin.setRoleName("ROLE_ADMIN");
        userRole_admin.setUserId(1L);
        userRole_regular1.setRoleName("ROLE_REGULAR");
        userRole_regular1.setUserId(2L);
        userRole_regular2.setRoleName("ROLE_REGULAR");
        userRole_regular2.setUserId(3L);
        mapper=mock(UserRoleMapper.class);
        session = mock(SqlSession.class);
        sessionFactory = mock(SqlSessionFactory.class);
        userRoleService = new UserRoleServiceImpl(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.getMapper(UserRoleMapper.class)).thenReturn(mapper);

    }

    @Test
    public void shouldFindUsersIdNotAdmin(){
        List<Long> expectedRoleList = new LinkedList<Long>();
        expectedRoleList.add(userRole_regular1.getUserId());
        expectedRoleList.add(userRole_regular2.getUserId());
        String userRole = "ROLE_REGULAR";
        when((mapper.getRegularUsersId(userRole))).thenReturn(expectedRoleList);

        List<Long> returnedRoleList = userRoleService.getAllNotAdmin();
        assertThat(returnedRoleList, is(expectedRoleList));

    }

    @Test
    public void shouldReturnUserRoleWhenGetByUserId(){
        Long id_user = 1L;
        UserRole userRole = new UserRole();
        userRole.setUserId(id_user);
        userRole.setRoleName("ROLE_REGULAR");
        when(mapper.get(id_user)).thenReturn(userRole);
        UserRole result = userRoleService.getByUserId(id_user);
        assertEquals(userRole,result);
    }

    @Test
    public void shouldUpdateUserRoleWhenAuthoriseRoleName(){
        userRoleService.authoriseRoleName(userRole_regular1);
        verify(mapper).authoriseUserRole(userRole_regular1);
    }
}

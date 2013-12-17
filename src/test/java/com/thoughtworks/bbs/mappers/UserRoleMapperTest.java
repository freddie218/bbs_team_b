package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.UserRole;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created with IntelliJ IDEA.
 * User: st
 * Date: 13-12-9
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleMapperTest extends MapperTestBase{
    UserRoleMapper userRoleMapper;
    UserRole userRole_admin;
    UserRole userRole_regular1;
    UserRole userRole_regular2;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        userRoleMapper = getSqlSession().getMapper(UserRoleMapper.class);
        userRole_admin = new UserRole();
        userRole_regular1 = new UserRole();
        userRole_regular2 = new UserRole();

        userRole_admin.setRoleName("ROLE_ADMIN");
        userRole_admin.setUserId(1L);
        userRole_regular1.setRoleName("ROLE_REGULAR");
        userRole_regular1.setUserId(2L);
        userRole_regular2.setRoleName("ROLE_REGULAR");
        userRole_regular2.setUserId(3L);
    }
    @Test
    public void shouldFindAllRegularUser(){
        int before = userRoleMapper.getRegularUsersId("ROLE_REGULAR").size();
        userRoleMapper.insert(userRole_regular1);
        userRoleMapper.insert(userRole_regular2);
        assertThat(userRoleMapper.getRegularUsersId("ROLE_REGULAR").size(),is(before + 2));


    }

    @Test
    public void shouldReturnUserRoleWhenGetByUserId(){
        UserRole userRole = new UserRole();
        userRole.setRoleName("ROLE_REGULAR");
        userRole.setUserId(8L);
        userRoleMapper.insert(userRole);
        UserRole result = userRoleMapper.get(userRole.getUserId());
        assertEquals(userRole.getRoleName(), result.getRoleName());
        assertEquals(userRole.getUserId(),result.getUserId());
    }

    @Test
    public void shouldUpdateRoleNameWhenAuthoriseUserRole(){
        UserRole userRole = new UserRole();
        userRole.setRoleName("ROLE_ADMIN");
        userRole.setUserId(8L);
        userRoleMapper.insert(userRole);
        userRoleMapper.authoriseUserRole(userRole);
        assertEquals("ROLE_ADMIN", userRole.getRoleName());

    }

}

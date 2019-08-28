package ad.home.service;

import ad.home.dao.mapper.base.UserMapper;
import ad.home.pojo.appentity.SecurityUser;
import ad.home.pojo.dbentity.SysRoleEntity;
import ad.home.pojo.dbentity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service("securityUserService")
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userMapper.queryUserByName(username);
        if( null == user ) {
            log.warn("数据库获取值为null");
            throw new UsernameNotFoundException("用户名不存在");
        }
        // RoleEntity role = roleMapper.selectById(user.getRoleId());
        //OrgEntity org = orgMapper.selectById(user.getOrgId());
        //if(null == org) {
        //    org = new OrgEntity();
        //}
        SysRoleEntity roleEntity = new SysRoleEntity();
        roleEntity.setRoleId(2);
        roleEntity.setRoleName("客户");
        roleEntity.setRoleEnName("clientuser");
        SecurityUser detailsUser = new SecurityUser(user, roleEntity);
        Set<GrantedAuthority> authoritiesSet = new HashSet<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + "normaluser"); // 普通用户
        authoritiesSet.add(authority);
        if( null != roleEntity) {
            GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + roleEntity.getRoleEnName()); // 用户角色
            authoritiesSet.add(roleAuthority);
        }
        detailsUser.setAuthorities(authoritiesSet);
        return detailsUser;
    }

}

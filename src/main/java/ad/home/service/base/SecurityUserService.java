package ad.home.service.base;

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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service("securityUserService")
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.0 根据用户名查询用户信息
        UserEntity user = userMapper.queryUserByName(username);
        if( null == user ) {
            log.warn("数据库获取值为null");
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 1.2 获取到用户信息（知道是谁登录）修改用户最后登录时间-并添加日志
        user.setLastLoginTime(new Date());
        userMapper.updateById(user);

        // 2.0 获取用户的角色及权限信息
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

        // 3.0 设置角色信息
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

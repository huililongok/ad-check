package ad.home.pojo.appentity;

import ad.home.pojo.dbentity.SysRoleEntity;
import ad.home.pojo.dbentity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SecurityUser extends UserEntity implements UserDetails {
    private static final long serialVersionUID = 1L;

    private UserEntity userEntity;

    // 用户角色
    @Getter
    @Setter
    private SysRoleEntity roleEntity;

    private Set<? extends GrantedAuthority> authorities;

    public SecurityUser(UserEntity user, SysRoleEntity role) {
        this.setUserId(user.getUserId());
        this.setUserName(user.getUserName());
        this.setPassword(user.getPassword());
        this.setUserEntity(user);
        this.setRoleEntity(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //String userName = this.getUserName();
        //if (StringUtils.isNotBlank(userName)) {
        //    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userName);
        //    authorities.add(authority);
        //}
        //return authorities;
        return this.authorities;
    }

    public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return this.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private List<String> rPath;

    public List<String> getrPath() {
        return rPath;
    }

    public void setrPath(List<String> rPath) {
        this.rPath = rPath;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}

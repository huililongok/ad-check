package ad.home.web.solve;

import ad.home.pojo.dbentity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionResolve {
    private static class SessionResolveHolder {
        private static SessionResolve instance = new SessionResolve();
    }

    public static SessionResolve getInstance() {
        return SessionResolveHolder.instance;
    }

    public UserEntity getSecuritySessionUser() {
        UserEntity user = null;
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            user = (UserEntity) auth.getPrincipal();
            user.setPassword("");
        }
        return user;
    }

}

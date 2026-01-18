package wiinvent.com.vn.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import wiinvent.com.vn.security.JwtData;

public class RequestContext {
    private RequestContext() {
    }

    public static String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User springUser) {
            return springUser.getUsername();
        }

        if (principal instanceof JwtData jwtData) {
            return jwtData.getUserId();
        }

        return null;
    }
}

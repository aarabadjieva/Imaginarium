package project.imaginarium.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import project.imaginarium.data.models.users.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {



    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {

        handle(request, response, authentication);
    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
    protected String determineTargetUrl(final Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("CLIENT", "/profile/client/" + user.getUsername());
        roleTargetUrlMap.put("ADMIN", "/profile/admin/" + user.getUsername());
        roleTargetUrlMap.put("PARTNER", "/profile/partner/" + user.getUsername());
        roleTargetUrlMap.put("GUIDE", "/profile/guide/" + user.getUsername());

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }
}

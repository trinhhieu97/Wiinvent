package wiinvent.com.vn.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    final String AUTH_HEADER = "Authorization";
    final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (Objects.nonNull(token) && tokenProvider.validateToken(token)) {

            JwtData jwtData = new JwtData(tokenProvider.getUsername(token), tokenProvider.getUserId(token));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(jwtData, null, Collections.emptyList());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(AUTH_HEADER);
        if (bearer != null && bearer.startsWith(TOKEN_PREFIX)) {
            return bearer.substring(7);
        }
        return null;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }
}

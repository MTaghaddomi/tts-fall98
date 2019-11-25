package ir.ac.kntu.config;

import ir.ac.kntu.service.UserService;
import ir.ac.kntu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String jwtToken = httpServletRequest.getHeader("Auth");
        String username = null;

        if(jwtToken != null){
            username = jwtUtil.token2username(jwtToken);
            logger.info(jwtToken);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userService.loadUserByUsername(username);

            if(jwtUtil.isValidToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken
                                (userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

        log(httpServletRequest, httpServletResponse, username);
    }


    private void log(HttpServletRequest request, HttpServletResponse response, String username) {
        String uri = request.getRequestURI();
        HttpStatus status = HttpStatus.valueOf(response.getStatus());

        StringBuilder logStr = new StringBuilder("details:\n");

        logStr.append("----> HTTP Request to <").append(uri).append(">\n");
        logStr.append("---->status: ").append(status).append("\n");

        logStr.append("---->username: ");
        logStr.append(username==null ? "unknown" : username).append("\n");

        logger.warn(logStr.toString());
    }
}

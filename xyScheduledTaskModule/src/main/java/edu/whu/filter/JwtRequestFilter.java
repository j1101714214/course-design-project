package edu.whu.filter;

import cn.hutool.core.util.StrUtil;
import edu.whu.exception.CustomerException;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Akihabara
 * @version 1.0
 * @description JwtRequestFilter: Jwt拦截器, 解析相关的用户信息并且存入SecurityContextHolder
 * @date 2023/9/16 18:52
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(Strings.isNullOrEmpty(header) || !header.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(BEARER.length());
            Claims body = JwtUtil.getClaimsBody(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(body.getSubject());
            if(userDetails != null && JwtUtil.verifyToken(body) == JwtUtil.VerifyResult.VALID) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception ex) {
            throw new CustomerException(ExceptionEnum.UN_AUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }
}

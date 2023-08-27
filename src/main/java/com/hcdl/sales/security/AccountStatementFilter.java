package com.hcdl.sales.security;

import com.hcdl.sales.service.AccountStatementServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class AccountStatementFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AccountStatementFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            MDC.put("username", principal.getUsername());
            logger.info("User name - " + principal.getUsername() + " logged in.......");
        }

        filterChain.doFilter(servletRequest, servletResponse);
        MDC.remove("username");
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}

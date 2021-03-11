package com.capitalone.dashboard.auth.token;

import com.capitalone.dashboard.util.CommonConstants;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;


@Component
@Order(2)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationFilter.class);
	private TokenAuthenticationService tokenAuthenticationService;
	
	@Autowired
	public JwtAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService){
		this.tokenAuthenticationService = tokenAuthenticationService;
	}
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request == null) return;

        long startTime = System.currentTimeMillis();
        String authHeader = request.getHeader("Authorization");
        String apiUser = request.getHeader(CommonConstants.HEADER_API_USER);
        String correlation_id = request.getHeader(CommonConstants.HEADER_CLIENT_CORRELATION_ID);
        apiUser = (StringUtils.isEmpty(apiUser)? "API_USER" : apiUser);
        correlation_id = (StringUtils.isEmpty(correlation_id)) ? "NULL" : correlation_id;
        if(response != null)
            response.addHeader(CommonConstants.HEADER_CLIENT_CORRELATION_ID, correlation_id);
        /*
         * apiToken based authentication
         */
        if (authHeader == null || authHeader.startsWith("apiToken ")) {
            try {
                filterChain.doFilter(request, response);
            } finally {
                String parameters = MapUtils.isEmpty(request.getParameterMap())? "NONE" :
                        Collections.list(request.getParameterNames()).stream()
                                .map(p -> p + ":" + Arrays.asList( request.getParameterValues(p)) )
                                .collect(Collectors.joining(","));
                LOGGER.info(" correlation_id=" + correlation_id + ", requester=" + (authHeader == null ? "READ_ONLY" : apiUser )
                        + ", duration=" + (System.currentTimeMillis() - startTime)
                        + ", uri=" + request.getRequestURI()
                        + ", request_method=" + request.getMethod()
                        + ", response_code=" + (response == null ? 0 : response.getStatus())
                        + ", client_ip=" + request.getRemoteAddr()
                        + (StringUtils.equalsIgnoreCase(request.getMethod(), "GET") ? ", request_params="+parameters :  StringUtils.EMPTY ));
            }
            return;
        }

        /*
         * username password based authentication
         */
        Authentication authentication = tokenAuthenticationService.getAuthentication(request);
        try {
            if (authentication == null) {
                //Handle Expired or bad JWT tokens
                LOGGER.info("Expired or bad JWT tokens, set response status to HttpServletResponse.SC_UNAUTHORIZED");
                if (response != null)
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                filterChain.doFilter(request, response);
            } else {
                // process properly authenticated requests
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
                tokenAuthenticationService.addAuthentication(response, authentication);
            }
        } finally {
            String parameters = MapUtils.isEmpty(request.getParameterMap())? "NONE" :
                    Collections.list(request.getParameterNames()).stream()
                            .map(p -> p + ":" + Arrays.asList( request.getParameterValues(p)) )
                            .collect(Collectors.joining(","));
            LOGGER.info("correlation_id=" + correlation_id + ", requester=" + ( authentication == null || authentication.getPrincipal() == null ? apiUser : authentication.getPrincipal() )
                    + ", duration=" + (System.currentTimeMillis() - startTime)
                    + ", uri=" + request.getRequestURI()
                    + ", request_method=" + request.getMethod()
                    + ", status=" + (response == null ? 0 : response.getStatus())
                    + ", client_ip=" + request.getRemoteAddr()
                    + (StringUtils.equalsIgnoreCase(request.getMethod(), "GET") ? ", request_params="+parameters :  StringUtils.EMPTY ));
        }
    }
}
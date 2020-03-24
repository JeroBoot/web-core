package com.jero.filter;

import com.jero.common.utils.StringUtils;
import com.jero.filter.wrapper.ExpiresHeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description Http缓存过滤器
 * @Author lixuetao
 * @Date 2020/3/24
 **/
public class HttpCacheFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpCacheFilter.class);

    private long maxAge = 60L * 60 * 24; //一天

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String maxAgeStr = filterConfig.getInitParameter("maxAge");
        if (StringUtils.isNotEmpty(maxAgeStr)) {
            maxAge = Long.valueOf(maxAgeStr);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.reset();
        filterChain.doFilter(request, new ExpiresHeaderResponse(response, maxAge));
    }

    @Override
    public void destroy() {}

}

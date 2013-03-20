package com.github.dirkraft.jerseyboot.app;

import org.apache.commons.lang3.CharEncoding;
import org.eclipse.jetty.servlet.FilterHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class StaticResourceUTF8CharEncodingFilterHolder extends FilterHolder {
    public StaticResourceUTF8CharEncodingFilterHolder() {
        super(new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
                // no-op
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                response.setCharacterEncoding(CharEncoding.UTF_8);
                chain.doFilter(request, response);
            }

            @Override
            public void destroy() {
                // no-op
            }
        });
    }
}

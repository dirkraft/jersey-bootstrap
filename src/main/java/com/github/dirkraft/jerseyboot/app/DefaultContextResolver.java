package com.github.dirkraft.jerseyboot.app;

import com.sun.jersey.api.core.InjectParam;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultContextResolver implements ContextResolver<ObjectMapper> {

    final ObjectMapper om;

    public DefaultContextResolver(@InjectParam DefaultObjectMapper objectMapper) {
        om = objectMapper;
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return om;
    }
}
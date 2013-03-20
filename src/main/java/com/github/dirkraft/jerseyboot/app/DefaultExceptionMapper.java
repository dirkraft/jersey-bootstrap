package com.github.dirkraft.jerseyboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.lang.Override;import java.lang.Throwable;import static javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Ensures that all exceptions are converted to json. {@link WebApplicationException}s will be left alone except for
 * ensuring that they come out as json.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        ResponseBuilder builder;
        if (exception instanceof WebApplicationException) {
            WebApplicationException webAppExcept = (WebApplicationException) exception;
            builder = Response.fromResponse(webAppExcept.getResponse());
        } else {
            LOG.warn("Internal server error (500)", exception);
            builder = Response.status(500).entity(exception);
        }
        return builder.type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}

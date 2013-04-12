package com.github.dirkraft.jerseyboot.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
@Path("/")
public class WelcomePageWeb {

    @GET
    public Response welcomePage() throws URISyntaxException {
        return Response.temporaryRedirect(new URI("/index.html")).build();
    }
}

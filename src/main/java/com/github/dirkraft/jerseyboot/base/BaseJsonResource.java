package com.github.dirkraft.jerseyboot.base;

import org.apache.commons.lang3.CharEncoding;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author jason
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=" + CharEncoding.UTF_8)
public abstract class BaseJsonResource {
}

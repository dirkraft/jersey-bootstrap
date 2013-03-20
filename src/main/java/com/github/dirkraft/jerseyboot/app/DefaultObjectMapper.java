package com.github.dirkraft.jerseyboot.app;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.annotation.Resource;

/**
 * @author jason
 */
@Resource
public class DefaultObjectMapper extends ObjectMapper {
    public DefaultObjectMapper() {
        configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}

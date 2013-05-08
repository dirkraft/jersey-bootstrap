package com.github.dirkraft.jerseyboot.web;

import com.github.dirkraft.jerseyboot.base.BaseConfig;
import com.github.dirkraft.jerseyboot.base.BaseJsonResource;
import com.github.dirkraft.propslive.dynamic.listen.PropChange;
import com.github.dirkraft.propslive.dynamic.listen.PropListener;
import com.github.dirkraft.propslive.set.ease.PropSetAsMap;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.Map;
import java.util.TreeMap;

import static com.github.dirkraft.jerseyboot.base.BaseConfig.$;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

/**
 * Controller for managing props. Changes to certain props should fire subscribed {@link PropListener#reload(PropChange)}
 *
 * @author Jason Dunkelberger (dirkraft)
 */
@Path("/_sys/props")
public class DynamicPropsWeb extends BaseJsonResource implements PropListener<String> {

    /**
     * System property that gives regex of properties to exclude from {@link #getProps(Boolean, String)} when passed
     * <code>false</code>. Defaults to <code>{@value #DEF_PROP_IGNORE_RGX}</code>.
     */
    public static final String PROP_PROP_IGNORE_RGX = "base.web.props.ignore";
    private static final String DEF_PROP_IGNORE_RGX = "^(" +
            "((awt|file|ftp|http|idea|java|line|os|path|sun|user)\\.)" +
            "|gopherProxySet|socksNonProxyHosts" +
            ").*$";
    static {
        // make the default visible in the config
        BaseConfig.DEFAULTS.setString(PROP_PROP_IGNORE_RGX, DEF_PROP_IGNORE_RGX);
    }

    String internalPropKeysRgx;
    final Predicate<String> notInternalPropKeys = new Predicate<String>() {
        @Override
        public boolean apply(@Nullable String input) {
            return input != null && !input.matches(internalPropKeysRgx);
        }
    };

    public DynamicPropsWeb() {
        this.internalPropKeysRgx = $.to(this).getString(PROP_PROP_IGNORE_RGX);
    }

    public DynamicPropsWeb(String internalPropKeysRgx) {
        this.internalPropKeysRgx = internalPropKeysRgx;
    }

    /**
     * @param includeHidden when true will show properties otherwise filtered by {@link #internalPropKeysRgx}
     */
    @GET
    public Map<String, String> getProps(@QueryParam(value = "includeHidden") Boolean includeHidden,
                                        @QueryParam(value = "filter") final String filterPattern) {
        Predicate<String> filter = isTrue(includeHidden) ? Predicates.<String>alwaysTrue() : notInternalPropKeys;
        if (StringUtils.isNotBlank(filterPattern)) {
            filter = Predicates.and(filter, new Predicate<String>() {
                public boolean apply(String input) {
                    return input.matches(filterPattern);
                }
            });
        }
        return new TreeMap<>(Maps.filterKeys($.asMap(), filter));
    }

    /**
     * @param updates to properties
     */
    @POST
    public void setSomeProps(final Map<String, String> updates) {
        $.setVals(new PropSetAsMap(updates));
    }

    @Override
    public void reload(PropChange<String> value) {
        this.internalPropKeysRgx = value.now();
    }
}

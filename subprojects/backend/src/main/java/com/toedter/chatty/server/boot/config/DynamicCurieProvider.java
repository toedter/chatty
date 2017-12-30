package com.toedter.chatty.server.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.IanaRels;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of {@link CurieProvider} rendering a single configurable {@link UriTemplate} based curie.
 * The base uri of the curie link is created dynamically if the URI template starts with '/'.
 */
public class DynamicCurieProvider implements CurieProvider {
    @Value("${spring.data.rest.base-path}")
    String baseUri = "";

    private Curie curie;
    private String name;
    private UriTemplate uriTemplate;

    /**
     * Creates a new {@link DynamicCurieProvider} for the given name and {@link UriTemplate}.
     *
     * @param name        must not be {@literal null} or empty.
     * @param uriTemplate must not be {@literal null} and contain exactly one template variable.
     */
    public DynamicCurieProvider(String name, UriTemplate uriTemplate) {

        Assert.hasText(name, "Name must not be null or empty!");
        Assert.notNull(uriTemplate, "UriTemplate must not be null!");
        Assert.isTrue(uriTemplate.getVariableNames().size() == 1,
                String.format("Expected a single template variable in the UriTemplate %s!", uriTemplate.toString()));

        if (!uriTemplate.toString().startsWith("/")) {
            this.curie = new Curie(name, uriTemplate.toString());
        } else {
            this.name = name;
            this.uriTemplate = uriTemplate;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.hal.CurieProvider#getCurieInformation()
     */
    @Override
    public Collection<? extends Object> getCurieInformation(Links links) {
        return Collections.singleton(getCurie());
    }

    @Override
    public String getNamespacedRelFrom(Link link) {
        return getNamespacedRelFor(link.getRel());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.hal.CurieProvider#getNamespacedRelFrom(java.lang.String)
     */
    @Override
    public String getNamespacedRelFor(String rel) {
        boolean prefixingNeeded = !IanaRels.isIanaRel(rel) && !rel.contains(":");
        return prefixingNeeded ? String.format("%s:%s", getCurie().name, rel) : rel;
    }

    private Curie getCurie() {
        if (curie == null) {
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString() + baseUri + uriTemplate.toString();
            this.curie = new Curie(name, uri);
        }
        return curie;
    }

    /**
     * Value object to get the curie {@link Link} rendered in JSON.
     *
     * @author Oliver Gierke
     */
    protected static class Curie extends Link {

        private static final long serialVersionUID = 1L;

        private final String name;

        public Curie(String name, String href) {

            super(href, "curies");
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

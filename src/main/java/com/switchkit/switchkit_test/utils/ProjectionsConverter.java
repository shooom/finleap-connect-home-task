package com.switchkit.switchkit_test.utils;

import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Component;

@Component
public class ProjectionsConverter {
    private final ProjectionFactory pf = new SpelAwareProxyProjectionFactory();

    public <P, E> P getProjection(Class<P> p, E e) {
        return pf.createProjection(p, e);
    }
}

/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */


package com.toedter.chatty.server.boot;

import com.toedter.chatty.server.boot.rest.DynamicCurieProvider;
import com.toedter.chatty.server.boot.service.ChatMessageRepositoryListener;
import com.toedter.chatty.server.boot.service.TestDataLoader;
import org.atmosphere.cpr.AtmosphereServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ChatMessageRepositoryListener eventRepositoryListener() {
        logger.debug("ChatMessageRepositoryListener created...");
        return new ChatMessageRepositoryListener();
    }

    @Bean(initMethod = "loadData")
    TestDataLoader RepositoryTestData() {
        return new TestDataLoader();
    }

    @Bean
    ServletRegistrationBean atmosphereServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new AtmosphereServlet(), "/chatty/atmos/*");
        Map<String, String> params = new HashMap<>();
        params.put("org.atmosphere.cpr.packages", "com.siemens.cis.server.event.pubsub");
        servletRegistrationBean.setInitParameters(params);
        logger.debug("Atmosphere servlet created...");
        return servletRegistrationBean;
    }

    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Bean
    public CurieProvider curieProvider() {
        return new DynamicCurieProvider("chatty", new UriTemplate("/profile/{rel}"));
    }
}

/*******************************************************************************
 * Copyright (C) Siemens AG, 2014. All Rights Reserved
 *
 * Transmittal, reproduction, dissemination and/or editing of this software as
 * well as utilization of its contents and communication thereof to others
 * without express authorization are prohibited. Offenders will be held liable
 * for payment of damages. All rights created by patent grant or registration of
 * a utility model or design patent are reserved.
 *
 * Contributors: Kai Tödter - initial API and implementation
 ******************************************************************************/

package com.toedter.chatty.server.boot;

import com.toedter.chatty.server.boot.service.ChatMessageRepositoryListener;
import com.toedter.chatty.server.boot.service.ChatMessageRepositoryTestData;
import org.atmosphere.cpr.AtmosphereServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setShowBanner(false);
        springApplication.run(args);
    }

    @Bean
    ChatMessageRepositoryListener eventRepositoryListener() {
        logger.debug("EventRepositoryListener created...");
        return new ChatMessageRepositoryListener();
    }

    @Bean(initMethod = "loadData")
    ChatMessageRepositoryTestData chatMessageRepositoryTestData() {
        return new ChatMessageRepositoryTestData();
    }

    @Bean
    ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet);
        servletRegistrationBean.addUrlMappings("/api/*");
        return servletRegistrationBean;
    }

    @Bean
    ServletRegistrationBean atmosphereServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new AtmosphereServlet(), "/atmos/*");
        Map<String, String> params = new HashMap<>();
        params.put("org.atmosphere.cpr.packages", "com.siemens.cis.server.event.pubsub");
        servletRegistrationBean.setInitParameters(params);
        logger.debug("Atmosphere servlet created...");
        return servletRegistrationBean;
    }

    @Configuration
    @ComponentScan(excludeFilters = @ComponentScan.Filter({Service.class, Configuration.class}))
    static class WebConfiguration {

        @Bean
        public CurieProvider curieProvider() {
            return new DefaultCurieProvider("chatty", new UriTemplate("http://localhost:8080/api/alps/{rel}"));
        }
    }
}

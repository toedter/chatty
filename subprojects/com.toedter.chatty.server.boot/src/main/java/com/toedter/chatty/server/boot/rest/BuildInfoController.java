package com.toedter.chatty.server.boot.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@PropertySource({ "classpath:build-info.properties" })
@BasePathAwareController
public class BuildInfoController implements ResourceProcessor<RepositoryLinksResource> {
    @Value( "${version}" )
    private String version = "?";
    @Value( "${timestamp}" )
    private String timeStamp = "?";

    private static final String BUILD_INFO = "buildinfo";
    private static final String API =  "chatty/api/";

    @RequestMapping("/" + BUILD_INFO)
    @ResponseBody
    public HttpEntity<BuildInfo> buildInfo() {
        BuildInfo buildInfo = new BuildInfo(version, timeStamp);
        buildInfo.add(linkTo(BuildInfoController.class).slash(API + BUILD_INFO).withSelfRel());

        return new ResponseEntity<>(buildInfo, HttpStatus.OK);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        resource.add(linkTo(BuildInfoController.class).slash(API + BUILD_INFO).withRel(BUILD_INFO));

        return resource;
    }

}
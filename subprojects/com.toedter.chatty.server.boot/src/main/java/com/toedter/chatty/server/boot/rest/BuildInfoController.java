package com.toedter.chatty.server.boot.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.util.Properties;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@PropertySource({ "classpath:build-info.properties" })
public class BuildInfoController {
    private static Logger logger = LoggerFactory.getLogger(BuildInfoController.class);

    @Value( "${version}" )
    private String version = "?";
    @Value( "${timestamp}" )
    private String timeStamp = "?";
    
    @RequestMapping("/chatty/api/buildinfo")
    @ResponseBody
    public HttpEntity<BuildInfo> buildInfo() {
        BuildInfo buildInfo = new BuildInfo(version, timeStamp);
        buildInfo.add(linkTo(methodOn(BuildInfoController.class).buildInfo()).withSelfRel());

        return new ResponseEntity<>(buildInfo, HttpStatus.OK);
    }
}
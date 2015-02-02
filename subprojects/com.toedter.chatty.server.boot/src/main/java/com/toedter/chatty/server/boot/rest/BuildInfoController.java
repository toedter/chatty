package com.toedter.chatty.server.boot.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class BuildInfoController {
    private static Logger logger = LoggerFactory.getLogger(BuildInfoController.class);
    
    @RequestMapping("/chatty/api/buildinfo")
    @ResponseBody
    public HttpEntity<BuildInfo> buildInfo() {
        String version = "?";
        String timeStamp = "?";

        Properties prop = new Properties();

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("/build-info.properties");
            if(stream != null) {
                prop.load(stream);

                version = prop.getProperty("version", "??");
                timeStamp = prop.getProperty("timestamp", "??");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        BuildInfo buildInfo = new BuildInfo(version, timeStamp);
        buildInfo.add(linkTo(methodOn(BuildInfoController.class).buildInfo()).withSelfRel());

        return new ResponseEntity<>(buildInfo, HttpStatus.OK);
    }
}
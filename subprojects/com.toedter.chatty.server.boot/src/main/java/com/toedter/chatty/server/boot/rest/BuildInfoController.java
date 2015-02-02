package com.toedter.chatty.server.boot.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class BuildInfoController {
    @RequestMapping("/chatty/api/buildinfo")
    @ResponseBody
    public HttpEntity<BuildInfo> buildInfo() {

        BuildInfo buildInfo = new BuildInfo("1.0.0");
        buildInfo.add(linkTo(methodOn(BuildInfoController.class).buildInfo()).withSelfRel());

        return new ResponseEntity<>(buildInfo, HttpStatus.OK);
    }
}
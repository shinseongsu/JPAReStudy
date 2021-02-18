package com.spring.rest.app;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.spring.rest.controller.EventController;
import com.spring.rest.domain.Event;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class EventResource extends Resource<Event> {
    public EventResource(Event content, Link... links) {
        super(content, links);
        add(linkTo(EventController.class).slash(content.getId()).withSelfRel());
    }
}

package com.rlve.matcher.api.domain;


import org.springframework.data.rest.core.config.Projection;

@Projection(name = "SimpleUser", types = { User.class })
public interface UserSimple {
    String getName();
}

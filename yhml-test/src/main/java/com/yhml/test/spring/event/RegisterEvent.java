/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yhml.test.spring.event;

import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {

    private static final long serialVersionUID = -6308141226225216271L;

    public RegisterEvent(String body) {
        super(body);
    }

}

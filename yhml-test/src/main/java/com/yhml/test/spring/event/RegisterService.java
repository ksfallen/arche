package com.yhml.test.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private ApplicationContext applicationContext;

    public void register() {
        System.out.println( "注册成功！");
        boolean ret = true;
        String body = "{\"name\": \"abc\", \"password\": \"123\"}";
        applicationContext.publishEvent(new RegisterEvent(body));
    }
}

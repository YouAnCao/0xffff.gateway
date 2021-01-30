package com.gateway.server.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * TODO
 * create by Lyon.Cao in 2021/01/19 0:42
 **/
@Component
public class SpringApplicationHolder implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationHolder.applicationContext = applicationContext;
    }
}

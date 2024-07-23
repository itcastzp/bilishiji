package com.example.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("context:" + applicationContext);
        this.applicationContext = applicationContext;
    }


    public static final <T> Object getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}

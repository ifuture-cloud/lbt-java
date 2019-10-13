package com.lordz.lbt.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author ：zzz
 */
public class SpringContext {

    private static ApplicationContext applicationContext;


    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }



    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> type) throws BeansException {
        return applicationContext.getBean(type);
    }

}

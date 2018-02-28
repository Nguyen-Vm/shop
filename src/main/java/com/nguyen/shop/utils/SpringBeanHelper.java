package com.nguyen.shop.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author RWM
 * @date 2018/2/8
 */
@Slf4j
public class SpringBeanHelper implements ApplicationContextAware {
    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz){
        try {
            return applicationContext.getBean(clazz);
        }catch (NoSuchBeanDefinitionException e){
            log.warn("no qualifying bean of type: {}", clazz);
            return null;
        }
    }
}

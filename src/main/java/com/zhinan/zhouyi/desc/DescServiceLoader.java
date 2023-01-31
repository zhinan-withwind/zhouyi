package com.zhinan.zhouyi.desc;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class DescServiceLoader implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Collection<?> descriptors = applicationContext.getBeansOfType(IDescriptor.class).values();
        descriptors.forEach(descriptor -> ((IDescriptor<?>) descriptor).register());
        log.info("共发现了{}个描述器", descriptors.size());
    }
}

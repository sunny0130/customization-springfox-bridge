package com.github.sunny0130.customization.springfox.bridge.spring.boot.starter.autoconfigure;

import com.github.sunny0130.customization.springfox.bridge.core.SpringfoxBridge;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@ConditionalOnClass(SpringfoxBridge.class)
@ConditionalOnExpression("${springfox.bridge.enabled:true}")
public class SpringfoxBridgeAutoConfiguration implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringfoxBridge.start(applicationContext);
    }
}

//package com.github.doublebin.springfox.bridge.demo;
//
//import com.github.doublebin.springfox.bridge.core.builder.annotations.BridgeApi;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.context.annotation.Configuration;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//
//@Aspect
//@Configuration
//@Slf4j
//public class BridgeApiAspect {
//
//    @Pointcut("@within(com.github.doublebin.springfox.bridge.core.builder.annotations.BridgeApi)")
//    //@Pointcut("execution(public * *(..))")
//    public void bridgeApiAnnotationPointcut() {
//    }
//
//    @SneakyThrows
//    @Before("bridgeApiAnnotationPointcut()")
//    public void beforeAdvice(JoinPoint joinPoint) {
//        log.info("进来了，奥！！！");
//        // 获取目标类
//        Class<?> targetClass = joinPoint.getTarget().getClass();
//        log.info("targetClass =======> {}",targetClass);
//        // 获取目标方法名
//        String methodName = joinPoint.getSignature().getName();
//        log.info("methodName =======> {}",methodName);
//        // 获取目标方法的参数类型
//        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
//        // 获取目标方法
//        Method targetMethod = targetClass.getMethod(methodName, parameterTypes);
//
//        // 获取目标方法上的注解
//        BridgeApi bridgeApiAnnotation = targetMethod.getAnnotation(BridgeApi.class);
//        log.info("targetClass: {}",targetClass);
//        BridgeApi bridgeApiAnnotationClass = targetClass.getAnnotation(BridgeApi.class);
//        log.info("bridgeApiAnnotationClass: {}",bridgeApiAnnotationClass);
//        // 修改注解中的tags属性
//        String[] tags = bridgeApiAnnotation.tags();
//        // 进行修改操作...
//        Field tagsField = bridgeApiAnnotation.getClass().getDeclaredField("tags");
//        tagsField.setAccessible(true);
//        // 修改tags字段的值
//        String[] modifiedTags = { "lin" };
//        tagsField.set(bridgeApiAnnotation, modifiedTags);
//        // 打印修改后的tags属性值
//        System.out.println("Modified tags: " + Arrays.toString(tags));
//    }
//}

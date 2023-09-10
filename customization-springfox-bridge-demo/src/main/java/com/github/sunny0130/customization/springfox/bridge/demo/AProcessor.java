package com.github.sunny0130.customization.springfox.bridge.demo;

import com.github.sunny0130.customization.springfox.bridge.core.builder.annotations.BridgeApi;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

@SupportedAnnotationTypes("com.github.sunny0130.customization.springfox.bridge.core.builder.annotations.BridgeApi")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@Slf4j
public class AProcessor extends AbstractProcessor {

    @SneakyThrows
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BridgeApi.class)) {
            // 获取目标类
            TypeElement classElement = (TypeElement) element;
            log.info("Processing annotation on class {}", classElement);

            // 获取目标类上的注解
            BridgeApi aAnnotation = classElement.getAnnotation(BridgeApi.class);
            log.info("Annotation value is {} ", aAnnotation);

            // 修改注解中的属性值
            String[] tags = aAnnotation.tags();
            log.info("Original tags: " + Arrays.toString(tags));

            // 进行修改操作...
            Field tagsField = aAnnotation.getClass().getDeclaredField("tags");
            tagsField.setAccessible(true);
            // 修改tags字段的值
            String[] modifiedTags = {"lin"};
            tagsField.set(aAnnotation, modifiedTags);
            // 打印修改后的tags属性值
            log.info("Modified tags: " + Arrays.toString(tags));
        }
        return true;
    }
}

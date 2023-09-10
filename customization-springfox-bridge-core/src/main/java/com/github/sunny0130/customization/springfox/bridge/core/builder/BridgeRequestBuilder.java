package com.github.sunny0130.customization.springfox.bridge.core.builder;

import com.github.sunny0130.customization.springfox.bridge.core.SpringfoxBridge;
import com.github.sunny0130.customization.springfox.bridge.core.builder.annotations.BridgeModelProperty;
import com.github.sunny0130.customization.springfox.bridge.core.component.tuple.Tuple2;
import com.github.sunny0130.customization.springfox.bridge.core.exception.BridgeException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javassist.*;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.github.sunny0130.customization.springfox.bridge.core.util.JavassistUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

@Slf4j
public class BridgeRequestBuilder {

    private static final ClassPool pool = ClassPool.getDefault();

    /**
     * @param method
     * @param simpleClassName
     * @return Tuple2.fst: Class , Tuple2.snd: is originalClass
     */
    public static Tuple2<Class, Boolean> newRequestClass(Method method, String simpleClassName) {
        Parameter[] parameters = method.getParameters();

        if (ArrayUtils.isEmpty(parameters)) {
            return null;
        }

        if (1 == parameters.length && null != parameters[0].getAnnotation(RequestBody.class)) {

            Type type = parameters[0].getParameterizedType();
            Class newReplaceClass = BridgeGenericReplaceBuilder.buildReplaceClass(type, null);
            return Tuple2.build(newReplaceClass, true);
        }

        String newClassName = BridgeClassNameBuilder.buildNewClassName(BridgeClassNameBuilder.NEW_REQUEST_CLASS_NAME_PRE, simpleClassName);

        try {
            CtClass newCtClass = pool.makeClass(newClassName);

            ClassFile ccFile = newCtClass.getClassFile();
            ConstPool constpool = ccFile.getConstPool();

            int i = 0;

            for (Parameter parameter : parameters) {

                Type type = parameter.getParameterizedType();
                Class parameterClass = BridgeGenericReplaceBuilder.buildReplaceClass(type, null);
                //Class parameterClass = parameter.getType();

                Annotation apiModelAnnotation = getApiModelAnnotation(constpool);
                JavassistUtil.addAnnotationForCtClass(newCtClass, apiModelAnnotation);

                pool.insertClassPath(new ClassClassPath(parameterClass));
                CtField ctField = new CtField(pool.get(parameterClass.getName()), "param" + i, newCtClass); //
                ctField.setModifiers(Modifier.PRIVATE);
                newCtClass.addField(ctField);

                Annotation apiModelParopertyAnnotation = getApiModelPropertyAnnotation(parameter, constpool);
                JavassistUtil.addAnnotationForCtField(ctField, apiModelParopertyAnnotation);

                JavassistUtil.addGetterForCtField(ctField);

                JavassistUtil.addSetterForCtField(ctField);

                i++;
            }
            newCtClass.writeFile(SpringfoxBridge.getBridgeClassFilePath());

            return Tuple2.build(newCtClass.toClass(), false);

        } catch (Exception e) {

            String parameterInfo = "";
            for (Parameter parameter : parameters) {
                parameterInfo += parameter.toString() + "|";
            }
            log.error("new request class failed, simple class name is {}, parameters are {}.", simpleClassName, parameterInfo, e);
            throw new BridgeException(e);
        }
    }

    private static Annotation getApiModelPropertyAnnotation(Parameter parameter, ConstPool constpool) {
        BridgeModelProperty bridgeModelProperty = parameter.getAnnotation(BridgeModelProperty.class);

        String[] annotationMethodNames = new String[]{"value", "name", "allowableValues", "access", "notes",
                "dataType", "required", "position", "hidden", "example", "readOnly", "reference"};

        return JavassistUtil.copyAnnotationValues(bridgeModelProperty, ApiModelProperty.class, constpool, annotationMethodNames);
    }

    private static Annotation getApiModelAnnotation(ConstPool constpool) {
        Annotation apiModelAnnotation = new Annotation(ApiModel.class.getName(), constpool);
        apiModelAnnotation.addMemberValue("description", new StringMemberValue("Assembled request class, desc.", constpool));
        return apiModelAnnotation;
    }


}

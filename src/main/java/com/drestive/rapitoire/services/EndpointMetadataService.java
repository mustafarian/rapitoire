package com.drestive.rapitoire.services;

import com.drestive.rapitoire.annotations.ReturnsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by mustafa on 20/08/2016.
 */

@Service
public class EndpointMetadataService {

    private final Logger log = LoggerFactory.getLogger(EndpointMetadataService.class);

    @Autowired
    protected RequestMappingHandlerMapping handlerMapping;

    public ControllerMethodInfo getControllerMethodInfo(String path, RequestMethod requestMethod) {

        Map<RequestMappingInfo, HandlerMethod> mappings = handlerMapping.getHandlerMethods();

        ControllerMethodInfo controllerMethodInfo = new ControllerMethodInfo();

        mappings.keySet().stream().forEach(mappingKey -> {
            if (mappingKey.getMethodsCondition().getMethods().contains(requestMethod)
                    && mappingKey.getPatternsCondition().getMatchingPatterns(path).size() > 0) {

                HandlerMethod method = mappings.get(mappingKey);
                MethodParameter params[] = method.getMethodParameters();

                for (MethodParameter param : params) {
                    Annotation annotation = param.getParameterAnnotation(RequestBody.class);
                    if (annotation != null) {
                        Class clazz = param.getParameterType();
                        log.info("Parameter Type: " + clazz.getName());
                        controllerMethodInfo.setRequestBodyParamClass(clazz);
                        break;
                    }
                }

                ReturnsList returnsList = method.getMethodAnnotation(ReturnsList.class);
                if (returnsList != null) {
                    controllerMethodInfo.setReturnsList(true);
                } else {
                    controllerMethodInfo.setReturnsList(false);
                }
            }
        });
        return controllerMethodInfo;
    }
}

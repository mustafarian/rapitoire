package com.drestive.rapitoire.controllers;

import com.drestive.rapitoire.services.ControllerMethodInfo;
import com.drestive.rapitoire.services.EndpointMetadataService;
import com.drestive.rapitoire.services.RecordDescriptorGenerator;
import com.drestive.rapitoire.dtos.InputGroupDto;
import com.drestive.rapitoire.dtos.ServiceDescriptorDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mustafa on 18/08/2016.
 */
public class AbstractRecordDescriptorController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(AbstractRecordDescriptorController.class);

    public static final String PATH_PARAM = "path";
    public static final String METHOD_PARAM = "method";

    public static final Map<String, ControllerMethodInfo> controllerMethodRegistry = new HashMap<>();

    @Autowired
    protected EndpointMetadataService endpointMetadataService;

    @Autowired
    protected RecordDescriptorGenerator recordDescriptorGenerator;

    protected void registerControllerMethod(String path, Class clazz, String controllerPath) {
        registerControllerMethod(path, clazz, controllerPath, false);
    }

    protected void registerControllerMethod(String path, Class clazz, String controllerPath, Boolean returnsList) {
        controllerMethodRegistry.put(path, new ControllerMethodInfo(clazz, controllerPath, returnsList));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ServiceDescriptorDto> describe(HttpServletRequest request,
                                                         @RequestParam(name = PATH_PARAM) String servicePath,
                                                         @RequestParam(name = METHOD_PARAM) String method) {
        switch (method.toUpperCase()) {
        case "GET":
            return describe(request, servicePath, RequestMethod.GET);
        case "POST":
            return describe(request, servicePath, RequestMethod.POST);
        case "PATCH":
            return describe(request, servicePath, RequestMethod.PATCH);
        default:
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    public ResponseEntity<ServiceDescriptorDto> describe(HttpServletRequest httpServletRequest,
                                                         String servicePath,
                                                         RequestMethod method) {
        log.info("Locating Input Classes for: " + servicePath);

        ControllerMethodInfo controllerMethodInfo = endpointMetadataService
                .getControllerMethodInfo(servicePath, method);

        InputGroupDto inputGroupDto;
        String targetServicePath;
        if (controllerMethodInfo == null || controllerMethodInfo.getRequestBodyParamClass() != null) {
            inputGroupDto = recordDescriptorGenerator.generate(controllerMethodInfo.getRequestBodyParamClass(), method,
                    controllerMethodInfo.getReturnsList());
            if (inputGroupDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            targetServicePath = httpServletRequest.getContextPath() + servicePath;
        } else { // try getting DTO class from registry
            controllerMethodInfo = controllerMethodRegistry.get(servicePath);
            if (controllerMethodInfo == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                inputGroupDto = recordDescriptorGenerator
                        .generate(controllerMethodInfo.getRequestBodyParamClass(), method,
                                controllerMethodInfo.getReturnsList());
                if (inputGroupDto == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
            targetServicePath = httpServletRequest.getContextPath() + "/" + controllerMethodInfo.getControllerPath();
        }

        ServiceDescriptorDto serviceDescriptorDto = new ServiceDescriptorDto(targetServicePath, inputGroupDto);
        return new ResponseEntity<>(serviceDescriptorDto, HttpStatus.OK);
    }
}

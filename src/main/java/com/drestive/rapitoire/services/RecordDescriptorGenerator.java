package com.drestive.rapitoire.services;

import com.drestive.rapitoire.annotations.*;
import com.drestive.rapitoire.dtos.ElementDto;
import com.drestive.rapitoire.dtos.InputGroupDto;
import com.drestive.rapitoire.dtos.SelectOptionDto;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mustafa on 20/08/2016.
 */

@Service
public class RecordDescriptorGenerator {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(RecordDescriptorGenerator.class);

    @Autowired
    protected SelectOptionsGenerator selectOptionsGenerator;

    /**
     * Generate An InputGroupDto object for a DTO class and a request method.
     * @param dtoClass
     * @param requestMethod
     * @param returnsList
     * @return
     */
    public InputGroupDto generate(Class dtoClass, RequestMethod requestMethod, Boolean returnsList) {
        if (dtoClass == null) {
            throw new IllegalArgumentException("DTO Class should not be null.");
        }

        InputGroupDto inputGroup = null;
        Group groupAnnotation = (Group) dtoClass.getAnnotation(Group.class);
        if (groupAnnotation != null) {
            inputGroup = new InputGroupDto();
            inputGroup.setName(groupAnnotation.name());

            for (Field field : FieldUtils.getFieldsWithAnnotation(dtoClass, Element.class)) {
                Element elementAnnotation = field.getAnnotation(Element.class);

                List<SelectOptionDto> options = null;
                if (elementAnnotation.type() == ElementType.SELECT && elementAnnotation.options().length() > 0) {
                    options = selectOptionsGenerator.getInputElementOptions(elementAnnotation);
                }

                if (include(requestMethod, returnsList, elementAnnotation.includeFor())) {
                    ElementDto element = new ElementDto(field.getName(), elementAnnotation.label(),
                            elementAnnotation.type().getName(), elementAnnotation.required(),
                            !isElementWritable(requestMethod, elementAnnotation.permitWriteFor()),
                            elementAnnotation.source(), elementAnnotation.groupLabel(), options);
                    inputGroup.getElements().add(element);
                }
            }
        }

        return inputGroup;
    }

    /**
     * An element's include options are checked against the HTTP Request Method and whether the controller method
     * returns a list of entities. If one of the options in the includeOptions array received matches with the
     * requestMethod and controller method return type, the element this method returns true.
     *
     * @param requestMethod
     * @param returnsList
     * @param includeOptions
     * @return
     */
    protected Boolean include(RequestMethod requestMethod, Boolean returnsList, IncludeOptions includeOptions[]) {
        List optionsList = Arrays.asList(includeOptions);
        Boolean include = false;
        switch (requestMethod) {
        case POST:
            if (optionsList.contains(IncludeOptions.CREATE)) {
                include = true;
            }
            break;
        case PATCH:
            if (optionsList.contains(IncludeOptions.UPDATE)) {
                include = true;
            }
            break;
        case GET:
            if (!returnsList && optionsList.contains(IncludeOptions.READ)) {
                include = true;
            } else if (returnsList && optionsList.contains(IncludeOptions.LIST)) {
                include = true;
            }
            break;
        default:
            include = false;
        }

        return (include || optionsList.contains(IncludeOptions.ALL) || (optionsList.contains(IncludeOptions.EXCEPT_LIST)
                && !returnsList));
    }

    /**
     * A utility method that checks a request http method against a list of element permissions to determine
     * if the element can be written at this request method.
     * @param requestMethod
     * @param permitWriteOptions
     * @return
     */
    protected Boolean isElementWritable(RequestMethod requestMethod, PermitWriteOptions permitWriteOptions[]) {
        List optionsList = Arrays.asList(permitWriteOptions);
        Boolean include = false;
        switch (requestMethod) {
        case POST:
            if (optionsList.contains(PermitWriteOptions.CREATE)) {
                include = true;
            }
        case PATCH:
            if (optionsList.contains(PermitWriteOptions.UPDATE)) {
                include = true;
            }
        default:
            return include || optionsList.contains(PermitWriteOptions.ALL);
        }
    }
}
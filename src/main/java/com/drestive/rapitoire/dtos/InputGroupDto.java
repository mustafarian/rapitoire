package com.drestive.rapitoire.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 21/08/2016.
 */

/**
 * Holds a list of ElementDto's that in turn describe a DTO to a consumer of the API.
 */
public class InputGroupDto implements Serializable {
    protected String name;
    protected List<ElementDto> elements = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ElementDto> getElements() {
        return elements;
    }

    public void setElements(List<ElementDto> elements) {
        this.elements = elements;
    }
}

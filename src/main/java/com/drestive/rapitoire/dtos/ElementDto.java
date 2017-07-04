package com.drestive.rapitoire.dtos;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mustafa on 21/08/2016.
 */

/**
 * Describes an element that makes a up the input/out object of a REST API call. This is meant to
 * help a client build the ui input element types and their constraints.
 */
public class ElementDto implements Serializable {
    protected String name;
    protected String label;
    protected String type;
    protected Boolean required;
    protected Boolean readOnly;
    protected String source;
    protected String groupLabel;
    protected List<SelectOptionDto> options;

    public ElementDto(String name,
                      String label,
                      String type,
                      Boolean required,
                      Boolean readOnly,
                      String source,
                      String groupLabel,
                      List<SelectOptionDto> options) {
        this.name = name;
        this.label = label;
        this.type = type;
        this.required = required;
        this.readOnly = readOnly;
        this.source = source;
        this.groupLabel = groupLabel;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGroupLabel() {
        return groupLabel;
    }

    public void setGroupLabel(String groupLabel) {
        this.groupLabel = groupLabel;
    }

    public List<SelectOptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<SelectOptionDto> options) {
        this.options = options;
    }
}

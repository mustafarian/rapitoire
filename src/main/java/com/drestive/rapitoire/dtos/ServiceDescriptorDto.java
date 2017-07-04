package com.drestive.rapitoire.dtos;

import java.io.Serializable;

/**
 * Created by mustafa on 15/11/2016.
 */
public class ServiceDescriptorDto implements Serializable {
    protected String serviceEndpoint;
    protected InputGroupDto entity;

    public ServiceDescriptorDto() {
    }

    public ServiceDescriptorDto(String serviceEndpoint, InputGroupDto entity) {
        this.serviceEndpoint = serviceEndpoint;
        this.entity = entity;
    }

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public InputGroupDto getEntity() {
        return entity;
    }

    public void setEntity(InputGroupDto entity) {
        this.entity = entity;
    }
}

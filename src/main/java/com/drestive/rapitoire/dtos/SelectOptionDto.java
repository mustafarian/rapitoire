package com.drestive.rapitoire.dtos;

import java.io.Serializable;

/**
 * Created by mustafa on 20/10/2016.
 */
public class SelectOptionDto implements Serializable{
    protected Serializable id;
    protected String name;

    public SelectOptionDto() {
    }

    public SelectOptionDto(Serializable id, String name) {
        this.id = id;
        this.name = name;
    }

    public Serializable getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

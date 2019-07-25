package com.kdgc.hand.domain;


import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String name;  //人员名称

    public String getId() {
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

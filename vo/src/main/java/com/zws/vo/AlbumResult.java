package com.zws.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({ "handler","hibernateLazyInitializer","fieldHandler"})

public class AlbumResult  implements Serializable {
    private Long id;
    private String  name;
    private String head;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }




    public AlbumResult(Long id, String name, String head) {
        this.id = id;
        this.name = name;
        this.head = head;
    }




}

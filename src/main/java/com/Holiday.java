package com;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

//@JsonRootName(value = "holiday")
public class Holiday {
    @JsonSerialize(using = MyDateSerializer.class)
    @JsonDeserialize(using = MyDateDeserializer.class)
    @JsonProperty("date")
    private Date date;
    @JsonProperty("name")
    private String name;
    @JsonProperty("class")
    private String classH;


    public Holiday() {
    }

    public Holiday(Date date, String name, String classH) {
        this.date = date;
        this.name = name;
        this.classH = classH;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassH() {
        return classH;
    }

    public void setClassH(String classH) {
        this.classH = classH;
    }
}

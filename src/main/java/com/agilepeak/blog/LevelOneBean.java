package com.agilepeak.blog;

import java.util.List;

public class LevelOneBean extends AbstractBean {

    private String levelOneString;

    private Integer levelOneInteger;

    private List<String> levelOneList;

    public LevelOneBean(String levelOneString, Integer levelOneInteger, List<String> levelOneList) {
        this.levelOneString = levelOneString;
        this.levelOneInteger = levelOneInteger;
        this.levelOneList = levelOneList;
    }

    // inherits equals and hashcode from superclass which only uses id and version
}

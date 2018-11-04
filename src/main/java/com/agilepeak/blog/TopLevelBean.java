package com.agilepeak.blog;

import java.time.LocalDate;

public class TopLevelBean extends AbstractBean {

    private String stringField;

    private int intField;

    private boolean booleanField;

    private LocalDate localDateField;

    private LevelOneBean levelOneBean;

    public TopLevelBean(String id, long version, String stringField, int intField, boolean booleanField, LocalDate localDateField, LevelOneBean levelOneBean) {
        this.id = id;
        this.version = version;
        this.stringField = stringField;
        this.intField = intField;
        this.booleanField = booleanField;
        this.localDateField = localDateField;
        this.levelOneBean = levelOneBean;
    }

    // inherits equals and hashcode from superclass which only uses id and version
}

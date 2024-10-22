package edu.ada.t1172.groupfive.imdbdataanalyzer.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseModel {
    protected final String uuid;
    protected LocalDateTime createdAt;
    protected String createdBy;

    public BaseModel() {
        this.uuid = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
}

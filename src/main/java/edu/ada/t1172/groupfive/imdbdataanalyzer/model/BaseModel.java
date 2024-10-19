package edu.ada.t1172.groupfive.imdbdataanalyzer.model;

import java.time.LocalDateTime;
import java.util.UUID;

// TODO: esta classe também foi criada pensando em bancos de dados, provavelmente o DTO não terá relação com ela
public abstract class BaseModel {
    protected final String uuid;
    protected LocalDateTime createdAt;
    protected String createdBy;

    public BaseModel() {
        this.uuid = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
}

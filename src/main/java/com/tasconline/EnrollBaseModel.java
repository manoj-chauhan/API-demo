package com.tasconline;

import java.util.ArrayList;

public class EnrollBaseModel {
    private String id;
    private int version = 1;
    private String producerId;
    private String eventCorrelationId;
    private String created ="2018-10-01T21:22:40.133Z";
    private String createdBy;
    private String type;
    private String createdById;
    private EnrollDataModel data = new EnrollDataModel();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getEventCorrelationId() {
        return eventCorrelationId;
    }

    public void setEventCorrelationId(String eventCorrelationId) {
        this.eventCorrelationId = eventCorrelationId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String creatededBy) {
        this.createdBy = creatededBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EnrollDataModel getData() {
        return data;
    }

    public void setData(EnrollDataModel data) {
        this.data = data;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }
}

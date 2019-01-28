package com.abhinav.learn_spring.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    protected Long id;

    @Column(name = "created_by", updatable = false)
    protected String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, insertable = true, updatable = false)
    protected Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_on", nullable = false, insertable = true, updatable = true)
    protected Date lastModifiedOn;

    @Version
    @Column(name = "version")
    protected Long version = 0L;

    @PrePersist
    protected void onCreate() {
        lastModifiedOn = createdOn = (Objects.isNull(createdOn) ? new Date() : createdOn);
        //createdBy = (Objects.isNull(createdBy) || createdBy.equals("")) ? Context.getContextInfo().getLoginId() : createdBy;
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedOn = new Date();
    }
}

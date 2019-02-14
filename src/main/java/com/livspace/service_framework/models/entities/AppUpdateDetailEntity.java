package com.livspace.service_framework.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "app_update_details")
@EqualsAndHashCode(callSuper=false)
public class AppUpdateDetailEntity extends BaseEntity {
    @Column(name = "message")
    private String message;
    @JoinColumn(name = "app_update_id", referencedColumnName = "id")
    @ManyToOne
    private AppUpdateConfigEntity appUpdateConfigEntity;
}

package com.abhinav.learn_spring.models.entities;


import com.abhinav.learn_spring.models.StringListAttributeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "app_update_config")
@EqualsAndHashCode(callSuper = false)
public class AppUpdateConfigEntity extends BaseEntity {
    @Column(name = "version_no")
    private Long versionNo;
    @Column(name = "update_type")
    private String updateType;
    @Column(name = "uids")
    @Convert(converter = StringListAttributeConverter.class)
    private List<String> eligibleUserEmailIds;
    @Column(name = "download_url")
    private String downloadUrl;
}

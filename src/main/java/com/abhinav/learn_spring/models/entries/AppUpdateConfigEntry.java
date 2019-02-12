package com.abhinav.learn_spring.models.entries;

import com.abhinav.learn_spring.models.enums.UpdateType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppUpdateConfigEntry extends BaseEntry {
    private Long versionNo;
    private List<String> eligibleUserEmailIds;
    private String downloadUrl;
    private String updateType;
    private Boolean deleted;
}

package com.livspace.service_framework.models.entries;

import com.livspace.service_framework.models.enums.UpdateType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppUpdateConfigEntry extends BaseEntry {
    private Long versionNo;
    private List<String> eligibleUserEmailIds;
    private String downloadUrl;
    private UpdateType updateType;
    private Boolean deleted;
    private List<AppUpdateDetailEntry> appUpdateDetail;
}

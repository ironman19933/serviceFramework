package com.livspace.service_framework.models.entries;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppUpdateDetailEntry extends BaseEntry {
    private Long appUpdateId;
    private String message;
}

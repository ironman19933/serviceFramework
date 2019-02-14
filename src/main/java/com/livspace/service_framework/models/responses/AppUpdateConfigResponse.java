package com.livspace.service_framework.models.responses;

import com.livspace.service_framework.models.entries.AppUpdateConfigEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AppUpdateConfigResponse extends BaseResponse {
    private List<AppUpdateConfigEntry> appUpdateConfigEntries;
}

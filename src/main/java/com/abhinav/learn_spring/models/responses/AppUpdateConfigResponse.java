package com.abhinav.learn_spring.models.responses;

import com.abhinav.learn_spring.models.entries.AppUpdateConfigEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AppUpdateConfigResponse extends BaseResponse {
    private AppUpdateConfigEntry appUpdateConfigEntry;
    private List<AppUpdateConfigEntry> appUpdateConfigEntries;
}

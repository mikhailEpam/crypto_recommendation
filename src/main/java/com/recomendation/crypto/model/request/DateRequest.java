package com.recomendation.crypto.model.request;

import lombok.Data;

@Data
public class DateRequest {

    private Long startTime;
    private Long endTime;

    public Long getStartTime() {
        return startTime != null ? startTime : 0L;
    }

    public Long getEndTime() {
        return endTime != null ? endTime : Long.MAX_VALUE;
    }
}

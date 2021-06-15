package com.bwc.mydata.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String rsp_code;
    private String rsp_msg;

}

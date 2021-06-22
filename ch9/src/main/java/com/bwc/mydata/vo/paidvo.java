package com.bwc.mydata.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class paidvo {

    private String rsp_code;
    private String rsp_msg;

    private List<paidMethodvo> accountList;

}

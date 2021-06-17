package com.bwc.mydata.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class paidMethodvo {

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String payid;
    private String payname;
    private int amt;

}

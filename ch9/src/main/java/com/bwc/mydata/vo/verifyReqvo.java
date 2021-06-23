package com.bwc.mydata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class verifyReqvo {

    @NotEmpty(message = "필수값입니다.")
    @Size(min = 1, max = 100, message = "100자 이상 넘길 수 없습니다.")
    private String ci;

    @Size(min = 0, max = 2, message = "2자 이상 넘길 수 없습니다.")
    @Pattern(regexp = "^[0-9a-z]*$", message = "숫자와 알파벳만 넣어주세요.")
    private String req_type;

    @Size(min = 0, max = 2, message = "2자 이상 넘길 수 없습니다.")
    @Pattern(regexp = "^[0-9a-z]*$", message = "숫자와 알파벳만 넣어주세요.")
    private String used_auth_type;

}

package com.bwc.mydata.vo;

import lombok.Data;

@Data
public class Member {
    private long idx;

    private String email;

    private String name;

    public Member() {
    }

    public Member(long idx, String email, String name) {
        this.email = email;
        this.name = name;
    }

}

package com.bwc.mydata.repository;

import com.bwc.mydata.vo.Member;

public interface MemberRepository {
    public Member findByNameNoCache(String name);
    public Member findByNameCache(String name);
    public void refresh(String name);
}

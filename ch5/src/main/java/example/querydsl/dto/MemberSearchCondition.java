package example.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {

    private String username;
    private String teamName;
    private Integer ageGoe; // Integer wrapper 객체를 쓰는 이유, 값이 null일 수도 있어서
    private Integer ageLoe;

}

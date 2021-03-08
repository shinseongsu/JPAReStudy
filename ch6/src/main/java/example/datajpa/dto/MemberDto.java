package example.datajpa.dto;

import example.datajpa.entity.Member;
import lombok.Data;

@Data
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    //DTO는 엔티티를 참조해도됨
    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }

}

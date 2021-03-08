package example.datajpa.repository;

public class UsernameOnlyDto {

    private final String username;

    /**
     * 생성자를 이름을 이용한 프로젝션
     */
    public UsernameOnlyDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}

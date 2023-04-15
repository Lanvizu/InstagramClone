package InstgramClone.startinsta.entity.dto;

import InstgramClone.startinsta.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String auth;
//    private MultipartFile profileImage;

    public static Member toEntity(MemberDTO dto) {
        return Member.builder()
//                .id(this.id)
                .username(dto.username)
                .password(dto.password)
                .email(dto.email)
                .phone(dto.phone)
                .auth(dto.auth)
                .build();
    }

    public static MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
//        dto.setId(this.id);
        dto.setUsername(member.getUsername());
        dto.setPassword(member.getPassword());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setAuth(member.getAuth());
        return dto;
    }
}

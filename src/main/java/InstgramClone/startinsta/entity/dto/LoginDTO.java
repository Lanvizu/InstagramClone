package InstgramClone.startinsta.entity.dto;

import InstgramClone.startinsta.entity.Member;
import InstgramClone.startinsta.login.MemberDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String auth;

    public static Member toEntity(LoginDTO dto) {
        return Member.builder()
                .id(dto.id)
                .username(dto.username)
                .password(dto.password)
                .email(dto.email)
                .phone(dto.phone)
                .auth(dto.auth)
                .build();
    }

    public static LoginDTO toDTO(Member member) {
        LoginDTO dto = new LoginDTO();
        dto.setId(member.getId());
        dto.setUsername(member.getUsername());
        dto.setPassword(member.getPassword());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setAuth(member.getAuth());
        return dto;
    }

    public static List<LoginDTO> toDTOList(List<Member> members) {
        return members.stream()
                .map(member -> toDTO(member))
                .collect(Collectors.toList());
    }
//
//    public static Long IdByAuthentication() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MemberDetail userDetails = (MemberDetail) authentication.getPrincipal();
//        Long memberId = userDetails.getId();
//        return memberId;
//    }

    public static MemberDetail detailByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetail userDetails = (MemberDetail) authentication.getPrincipal();
        return userDetails;
    }
}

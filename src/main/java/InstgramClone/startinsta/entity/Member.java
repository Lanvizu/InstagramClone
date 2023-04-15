package InstgramClone.startinsta.entity;

import InstgramClone.startinsta.entity.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;

    private String username;
    private String password;
    private String email;
    private String phone;
    private String auth;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberImage memberImage;

    public void changeProfileImage(MemberImage memberImage) {
        this.memberImage = memberImage;
        memberImage.setMember(this);
    }

    public void update(LoginDTO dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.auth = dto.getAuth();
    }
}

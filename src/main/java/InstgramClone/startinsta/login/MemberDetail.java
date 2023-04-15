package InstgramClone.startinsta.login;
import InstgramClone.startinsta.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

//회원보안
public class MemberDetail implements UserDetails {

    private Member member;

    public MemberDetail(Member member) {
        this.member = member;
    }

    public long getId() {
        return member.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return member.getAuth();
//            }
//        });
        collection.add(new SimpleGrantedAuthority(member.getAuth()));
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
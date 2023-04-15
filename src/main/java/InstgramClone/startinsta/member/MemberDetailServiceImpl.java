package InstgramClone.startinsta.member;

import InstgramClone.startinsta.entity.Member;
import InstgramClone.startinsta.login.MemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDetailServiceImpl implements UserDetailsService{

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        if(member == null){
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getAuth()));
        return new MemberDetail(member);
//        return new User(member.getUsername(), member.getPassword(), authorities);
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = memberRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getAuth()));
//        return new User(member.getUsername(), member.getPassword(), authorities);
//    }
}

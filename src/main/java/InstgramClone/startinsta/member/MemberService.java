package InstgramClone.startinsta.member;

import InstgramClone.startinsta.entity.MemberImage;
import InstgramClone.startinsta.entity.dto.LoginDTO;
import InstgramClone.startinsta.entity.dto.MemberDTO;
import InstgramClone.startinsta.entity.dto.MemberImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MemberService {

    MemberDTO validateAndSave(MemberDTO dto);

    Optional<LoginDTO> findUser(String username);

    LoginDTO loginSucceed(String username, String password);

    List<LoginDTO> findMembers();

    LoginDTO findId(Long memberId);

    LoginDTO userUpdate(Long memberId, LoginDTO dto);

    MemberImageDTO changeProfileImage(Long memberId, MultipartFile imageFile) throws IOException;

    MemberImageDTO profileImageById(Long memberId);
}

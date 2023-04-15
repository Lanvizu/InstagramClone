package InstgramClone.startinsta.member;

import InstgramClone.startinsta.entity.Member;
import InstgramClone.startinsta.entity.MemberImage;
import InstgramClone.startinsta.entity.dto.LoginDTO;
import InstgramClone.startinsta.entity.dto.MemberDTO;
import InstgramClone.startinsta.entity.dto.MemberImageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberImageRepository memberImageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //아이디 중복, 비밀번호 암호화, 기본 이미지 -> 저장
    public MemberDTO validateAndSave(MemberDTO dto) {
        Member duplicateMember = memberRepository.findByUsername(dto.getUsername());
        //중복이 아닐 경우 null
        if (duplicateMember == null) {
            //비밀번호 암호화
            dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            dto.setAuth("ROLE_USER");

            MemberImage basicImage = MemberImage.createBasicImage();
            Member member = MemberDTO.toEntity(dto);
            memberRepository.save(member);

            member.changeProfileImage(basicImage);
            memberImageRepository.save(basicImage);

            return dto;
        }
        return null;
    }

    public Optional<LoginDTO> findUser(String username) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            return Optional.empty();
        }
        return Optional.of(LoginDTO.toDTO(member));
    }

    public LoginDTO loginSucceed(String username, String password) {
        Member user = memberRepository.findByUsername(username);
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            LoginDTO dto = LoginDTO.toDTO(user);
            return dto;
        }else{
            return null;
        }
    }

    public List<LoginDTO> findMembers() {
        List<Member> memberList = memberRepository.findAll();
        List<LoginDTO> loginDTOS = LoginDTO.toDTOList(memberList);
        return loginDTOS;
    }

    public LoginDTO findId(Long memberId) {
        Member findId = memberRepository.findById(memberId)
                .orElse(null);
        LoginDTO dto = LoginDTO.toDTO(findId);
        return dto;
    }

    public LoginDTO userUpdate(Long memberId, LoginDTO dto) {
        Member member = memberRepository.findById(memberId)
                    .orElse(null);
        //비밀번호 암호화
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        dto.setAuth("ROLE_USER");
        member.update(dto);
        memberRepository.save(member);
        return dto;
    }

    public MemberImageDTO changeProfileImage(Long memberId, MultipartFile imageFile) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));

        String originalFileName = imageFile.getOriginalFilename();
        String storeFileName = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(originalFileName);
        MemberImage memberImage = new MemberImage(originalFileName, storeFileName, extension);

        member.changeProfileImage(memberImage);
        memberImageRepository.save(memberImage);
        MemberImageDTO imageDTO = MemberImageDTO.toDTO(memberImage);

        Path path = Paths.get("src/main/resources/static/img/profile-images/" + storeFileName + "." + extension);
        Files.write(path, imageFile.getBytes());

        return imageDTO;
        }

    public MemberImageDTO profileImageById(Long memberId) {
        MemberImage memberImage = memberImageRepository.findByMemberId(memberId)
                .orElse(null);
        return MemberImageDTO.toDTO(memberImage);
    }
}
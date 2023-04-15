package InstgramClone.startinsta.member;

import InstgramClone.startinsta.entity.MemberImage;
import InstgramClone.startinsta.entity.dto.LoginDTO;
import InstgramClone.startinsta.entity.dto.MemberDTO;
import InstgramClone.startinsta.entity.dto.MemberImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
//회원가입 및 관리 컨트롤러
public class MemberControllerImpl implements MemberController {

    private final MemberService memberService;

    //회원가입 페이지
    @GetMapping("/new")
    public String createForm() {
        return "members/createMemberForm";
    }
    @PostMapping("/new")
    public String createV2(@ModelAttribute MemberDTO dto) {
        MemberDTO memberId = (memberService.validateAndSave(dto));
        //아이디가 중복이면 null
        if (memberId == null) {
            return "members/createMemberForm";
        }
        return "redirect:/";
    }
    //회원 개인정보 페이지
    @GetMapping("/member/{memberId}")
    @PreAuthorize("#memberId == authentication.principal.id")
    public String user(@PathVariable Long memberId, Model model) {
            LoginDTO user = memberService.findId(memberId);
            model.addAttribute("user", user);
            return "members/member";
    }
    //개인정보 수정 페이지
    @GetMapping("/member/{memberId}/edit")
    @PreAuthorize("#memberId == authentication.principal.id")
    public String editForm(@PathVariable Long memberId, Model model) {
        LoginDTO user = memberService.findId(memberId);
        model.addAttribute("user", user);
        return "members/memberEditForm";
    }
    @PostMapping("/member/{memberId}/edit")
    public String editV2(@PathVariable Long memberId, @ModelAttribute LoginDTO dto, RedirectAttributes redirectAttributes) {
        LoginDTO member = memberService.userUpdate(memberId, dto);
        redirectAttributes.addAttribute("status", true);
        return ("redirect:/members/member/{memberId}/edit");
    }

    //프로필 이미지 변경
    @GetMapping("/member/{memberId}/image")
    @PreAuthorize("#memberId == authentication.principal.id")
    public String imageChange(@PathVariable("memberId") Long memberId, Model model) {
        LoginDTO user = memberService.findId(memberId);
        model.addAttribute("member", user);

        MemberImageDTO imageDTO = memberService.profileImageById(memberId);
        String imageName = imageDTO.getStoreFileName()+'.'+imageDTO.getExtension();
        model.addAttribute("memberImage",imageName);
        return ("main/imageChange");
    }
    @PostMapping("/member/{memberId}/image")
    public String createProfileImage(@PathVariable("memberId") Long memberId,
                                     @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        memberService.changeProfileImage(memberId, imageFile);
        return "redirect:/members/member/{memberId}";
    }
    //AUTH 테스트
//    @GetMapping("/user")
//    public String user2() {
//        return "user/basic";
//    }
//
//    @GetMapping("/admin")
//    public String admin() {
//        return "admin/basic";
//    }
}

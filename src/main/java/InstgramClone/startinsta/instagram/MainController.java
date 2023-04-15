package InstgramClone.startinsta.instagram;

import InstgramClone.startinsta.entity.MemberImage;
import InstgramClone.startinsta.entity.dto.LoginDTO;
import InstgramClone.startinsta.entity.dto.MemberImageDTO;
import InstgramClone.startinsta.login.MemberDetail;
import InstgramClone.startinsta.member.MemberService;
import InstgramClone.startinsta.post.entity.Post;
import InstgramClone.startinsta.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import static InstgramClone.startinsta.entity.dto.LoginDTO.detailByAuthentication;

@Controller
@RequiredArgsConstructor
//로그인 후 활동 컨트롤러
public class MainController {
    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/instagram/{memberId}")
    @PreAuthorize("#memberId == authentication.principal.id")
    public String main(@PathVariable("memberId") Long memberId, Model model) {
        LoginDTO user = memberService.findId(memberId);
        model.addAttribute("user", user);
        MemberImageDTO imageDTO = memberService.profileImageById(memberId);
        String imageName = imageDTO.getStoreFileName()+'.'+imageDTO.getExtension();
        model.addAttribute("memberImage",imageName);
        return "main/instagram";
    }

    @GetMapping("instagram/page")
    public String mainPage(Model model) {
        Long memberId = detailByAuthentication().getId();

        LoginDTO user = memberService.findId(memberId);
        model.addAttribute("user", user);

        MemberImageDTO image = memberService.profileImageById(memberId);
        String imageName = image.getStoreFileName()+'.'+image.getExtension();
        model.addAttribute("memberImage",imageName);

        //내림차순 리스트
        List<Post> postList = postService.getPostListByIdDesc();
        model.addAttribute("posts", postList);

        return ("main/page");
    }

    @GetMapping("/verification")
    public String check() {
        return "main/verification";
    }

    @PostMapping("/verification")
    //개인정보 수정전 확인
    public String checkByPassword(@RequestParam("password") String password,
                                         Model model) {
        String username = detailByAuthentication().getUsername();

        LoginDTO dto = memberService.loginSucceed(username, password);
        if (dto == null) {
//            model.addAttribute("status", true);
            return "main/verification";
        }
        return "redirect:/members/member/" + dto.getId();
    }
}
package InstgramClone.startinsta.login;

import InstgramClone.startinsta.entity.dto.LoginDTO;
import InstgramClone.startinsta.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
//로그인 과정 컨트롤러
public class LoginControllerImpl implements LoginController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/")
    //로그인
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        LoginDTO dto = memberService.loginSucceed(username, password);
        if (dto == null) {
//            model.addAttribute("errorMessage", "Invalid username or password");
            return "redirect:/";
        }
        long memberId = dto.getId();
//        model.addAttribute("member", dto);
        return "main/page";
    }

    @GetMapping("/findPassword")
    public String goFindPassword() {
        return "findPassword";
    }

    @PostMapping("/findPassword")
    //비밀번호 찾기
    public String findId(
            @RequestParam("username") String username,
            Model model) {
        Optional<LoginDTO> dtoOptional = memberService.findUser(username);
        if (dtoOptional.isEmpty()) {
            return ("/wrongUsername");
        }
        LoginDTO dto = dtoOptional.get();
        model.addAttribute("password", dto.getPassword());
        return ("yourPassword");
    }

    @GetMapping("/wrongUsername")
    public String goWrongUsernamePage() {
        return "wrongUsername";
    }

    @GetMapping("/members")
    //회원 리스트
    public String list(Model model) {
        List<LoginDTO> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberListV2";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

}

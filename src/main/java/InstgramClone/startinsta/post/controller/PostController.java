package InstgramClone.startinsta.post.controller;

import InstgramClone.startinsta.common.file.FileDTO;
import InstgramClone.startinsta.common.file.FileManage;
import InstgramClone.startinsta.entity.dto.LoginDTO;
import InstgramClone.startinsta.post.controller.dto.PostSaveForm;
import InstgramClone.startinsta.post.entity.Post;
import InstgramClone.startinsta.post.service.PostDTO;
import InstgramClone.startinsta.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final FileManage fileManage;

    //post 작성 페이지
    @GetMapping("/posts/write")
    public String post() {
        return "post/postWrite";
    }
    @PostMapping("/posts/write")
    public String addPost(@ModelAttribute PostSaveForm form, BindingResult bindingResult, Model model) throws Exception {
        //BindingResult = 요청 파라미터의 값이 유효하지 않을 경우, 데이터 바인딩과 유효성 검증 과정에서 발생한 오류를 처리
        if (!bindingResult.hasFieldErrors()) {
            List<FileDTO> fileDTOS = fileManage.storeImageFiles(form.getFiles());

            Long memberId = LoginDTO.detailByAuthentication().getId();

            postService.writePost(new PostDTO(memberId, fileDTOS, form.getText()));
            return "redirect:/instagram/page";
        }
        return "redirect:/posts/write";
    }

    //post 수정 페이지
    @GetMapping("/posts/update/{postId}")
    public String updatePost(@PathVariable Long postId, Model model) {
        Long memberId = LoginDTO.detailByAuthentication().getId();

        Post post = postService.getPost(postId);
        //post 작성자만 수정 가능
        if (post.getMember().getId() == memberId) {
            model.addAttribute("post", post);
            return "post/postUpdate";
        }
        return "redirect:/instagram/page";
    }
    @PostMapping("/posts/update/{postId}")
    public String updatePost2(@PathVariable Long postId, @ModelAttribute PostSaveForm form, @RequestParam String action) {
        //수정, 삭제 가능
        if (action.equals("modify")) {
            postService.modifyPostText(postId, form.getText());
        } else if (action.equals("delete")) {
            postService.deletePost(postId);
        }
        return "redirect:/instagram/page";
    }
}
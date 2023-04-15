package InstgramClone.startinsta.post.service;

import InstgramClone.startinsta.common.file.FileDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
//아이디 번호, 파일들, 텍스트를 담고 있는 DTO
public class PostDTO {
    private Long memberId;
    private List<FileDTO> fileDTOS;
    private String text;
}

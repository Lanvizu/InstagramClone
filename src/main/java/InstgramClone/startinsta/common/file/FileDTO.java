package InstgramClone.startinsta.common.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//파일의 업로드 파일명, 저장 파일명, 확장자 정보를 담은 DTO
public class FileDTO {
    private String uploadFileName;
    private String storeFileName;
    private String extension;
}

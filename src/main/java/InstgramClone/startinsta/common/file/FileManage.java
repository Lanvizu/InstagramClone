package InstgramClone.startinsta.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileManage {
    private String fileDir;

    public FileManage(@Value("${file.dir}") String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFullPath(String originalFileName) {
        return fileDir + originalFileName;
    }

    //이미지 파일들 리스트 저장
    public List<FileDTO> storeImageFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<FileDTO> fileDTOS = new ArrayList<>();
//        validateImageFiles(multipartFiles);
        for (MultipartFile multipartFile : multipartFiles) {
            fileDTOS.add(storeImageFile(multipartFile));
        }
        return fileDTOS;
    }

    //이미지 파일들이 맞는지 확인하는 메소드
//    private void validateImageFiles(List<MultipartFile> multipartFiles) {
//        for (MultipartFile multipartFile : multipartFiles) {
//            validateEmptyFile(multipartFile);
//            validateImageExtension(extractExtension(multipartFile.getOriginalFilename()));
//        }
//    }

    //이미지 파일 저장.
    public FileDTO storeImageFile(MultipartFile multipartFile) throws IOException {
//        validateEmptyFile(multipartFile);

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = UUID.randomUUID().toString();
        String extension = extractExtension(originalFileName);

//        validateImageExtension(extension);

        multipartFile.transferTo(new File(getFullPath(storeFileName + "." + extension)));

        return new FileDTO(extractFileName(originalFileName), storeFileName, extension);
    }

    //이미지 파일이 비어있는 경우 예외처리
//    private void validateEmptyFile(MultipartFile multipartFile) {
//        if (multipartFile.isEmpty()) {
//            throw EMPTY_FILE.getException();
//        }
//    }

    //이미지 파일의 종류 예외처리
//    private void validateImageExtension(String extension) {
//        if (!extension.matches("^(?i)(jpg|png|jpeg|)$")) {
//            throw INVALID_IMAGE_EXTENSION.getException();
//        }
//    }

    //파일 확장자 추출
    private String extractExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    //파일 이름 추출
    private String extractFileName(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("."));
    }

    public void deleteFile(String storeFileName, String extension) {
        File storedFile = new File(getFullPath(storeFileName + "." + extension));

        if (storedFile.exists()) {
            storedFile.delete();
        }
    }
}

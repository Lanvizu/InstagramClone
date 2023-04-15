package InstgramClone.startinsta.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import javax.persistence.*;
import java.io.File;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "image")
public class MemberImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String uploadFileName;
    private String storeFileName;
    private String extension;

    public MemberImage(String uploadFileName, String storeFileName, String extension) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
    }

    public static MemberImage createBasicImage() {
        File defaultImageFile = new File("src/main/resources/static/img/profile-images/default.png");
        String originalFileName = defaultImageFile.getName(); // 파일명 (예: default.jpg)
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1); // 파일 확장자 (예: jpg)
        String storeFileName = FilenameUtils.getBaseName(defaultImageFile.getName()); // 파일 저장 이름 (예: default)
        return new MemberImage(originalFileName, storeFileName, extension);
    }
    protected void setMember(Member member) {
        this.member = member;
    }

    public String getOriginalStoreFileName() {
        return storeFileName + "." + extension;
    }
}
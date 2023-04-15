package InstgramClone.startinsta.entity.dto;

import InstgramClone.startinsta.entity.MemberImage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberImageDTO {
    private Long id;
    private String uploadFileName;
    private String storeFileName;
    private String extension;

    public static MemberImage toEntity(MemberImageDTO dto) {
        return new MemberImage(dto.getUploadFileName(), dto.getStoreFileName(), dto.getExtension());
    }

    public static MemberImageDTO toDTO(MemberImage memberImage) {
        MemberImageDTO dto = new MemberImageDTO();
        dto.setId(memberImage.getId());
        dto.setUploadFileName(memberImage.getUploadFileName());
        dto.setStoreFileName(memberImage.getStoreFileName());
        dto.setExtension(memberImage.getExtension());
        return dto;
    }
}

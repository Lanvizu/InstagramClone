package InstgramClone.startinsta.post.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name ="POST_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private String uploadFileName;
    private String storeFileName;
    private String extension;

    public PostFile(String uploadFileName, String storeFileName, String extension) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
    }

    protected void setPost(Post post) {
        this.post = post;
    }

    public String getOriginalStoreFileName() {
        return storeFileName + "." + extension;
    }
}

package InstgramClone.startinsta.post.entity;

import InstgramClone.startinsta.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID",referencedColumnName = "MEMBER_ID")
    private Member member;

    @Lob
    private String text;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostFile> postFiles = new ArrayList<>();

    public Post(Member member, String text) {
        this.member = member;
        this.text = text;
    }

    public void addPostFile(PostFile postFile) {
        postFiles.add(postFile);
        postFile.setPost(this);
    }

    public void changeText(String text) {
        this.text = text;
    }
}

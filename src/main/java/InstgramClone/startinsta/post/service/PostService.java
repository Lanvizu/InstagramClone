package InstgramClone.startinsta.post.service;

import InstgramClone.startinsta.common.file.FileManage;
import InstgramClone.startinsta.entity.Member;
import InstgramClone.startinsta.member.MemberRepository;
import InstgramClone.startinsta.post.entity.Post;
import InstgramClone.startinsta.post.entity.PostFile;
import InstgramClone.startinsta.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FileManage fileManage;

    //포스트 작성 코드
    public Long writePost(PostDTO postDTO) throws Exception{
        Member member = memberRepository.findById(postDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
        Post post = new Post(member, postDTO.getText());
        //파일들을 불러와서 각각의 파일에 이름을 새로 지정해 저장.
        postDTO.getFileDTOS().forEach(file -> post.addPostFile(
                new PostFile(file.getUploadFileName(), file.getStoreFileName(), file.getExtension())));
        postRepository.save(post);
        return post.getId();
    }

    //내림차순으로 배치
    public List<Post> getPostListByIdDesc() {
        return postRepository.findAllByOrderByIdDesc();
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    @Transactional
    public void modifyPostText(Long postId, String modifiedText) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.changeText(modifiedText);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow();
        deletePostFiles(findPost);
        postRepository.deleteById(postId);
    }

    private void deletePostFiles(Post post) {
        post.getPostFiles().forEach((postFile) ->
                fileManage.deleteFile(postFile.getStoreFileName(), postFile.getExtension()));
    }
}
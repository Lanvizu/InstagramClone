package InstgramClone.startinsta.post.repository;

import InstgramClone.startinsta.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //id 내림차순 정리.
    List<Post> findAllByOrderByIdDesc();
}

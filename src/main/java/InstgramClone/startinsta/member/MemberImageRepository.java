package InstgramClone.startinsta.member;

import InstgramClone.startinsta.entity.MemberImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberImageRepository extends JpaRepository<MemberImage, Long> {
    Optional<MemberImage> findByMemberId(Long memberId);
}

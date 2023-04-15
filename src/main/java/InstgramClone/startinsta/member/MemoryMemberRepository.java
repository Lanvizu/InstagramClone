package InstgramClone.startinsta.member;

import InstgramClone.startinsta.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
public abstract class MemoryMemberRepository implements MemberRepository{

    private final EntityManager em;

    public MemoryMemberRepository(EntityManager em) {
        this.em = em;
    }

//    @Override
//    public List<Member> findAll() {
//        return em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//    }
}

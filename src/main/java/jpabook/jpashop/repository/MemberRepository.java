package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //자동으로 스프링 빈
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //em에 빈 주입
    private final EntityManager em;

    public void save(Member member) { //회원을 집어넣으면 jpa저장
        em.persist(member);
    }

    public Member findOne(Long id) { //조회
        return em.find(Member.class, id);//member를 찾아서 조회
    }

    public List<Member> findAll() {//전최 조회
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {//이름으로 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
    }


}

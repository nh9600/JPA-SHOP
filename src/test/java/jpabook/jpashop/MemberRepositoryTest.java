package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository1 memberRepository;

    @Test
    @Transactional //테스트가 끝나면 DB롤백
    @Rollback(value = false) //롤백 취소
    public void testMember() throws Exception {
        //given
        Member1 member = new Member1();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member1 findMember = memberRepository.find(savedId);

        //then (검증)
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member=" + (findMember == member));

    }

}
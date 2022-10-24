package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.commons.function.Try;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //junit 실행 할 때 스프링이랑 같이 할래?
@SpringBootTest //스프링부트를 띄운 상태에서 돌려야 함
@Transactional //테스트가 끝나면 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    //@Rollback(value = false) //롤백 안 하고
    public void 회원가입() throws Exception {
        //given 주어졌을 때
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        //em.flush(); 콘솔창에 보이게
        assertEquals(member, memberRepository.findOne(saveId)); //저장한 멤버랑 저장소에 있는 멤버랑 같은 지

    }

    @Test(expected = IllegalStateException.class) //try ~ catch
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");
        
        //when
        memberService.join(member1);
        memberService.join(member2);//예외가 발생해야 함

        //then
        fail("예외가 발생해야 한다.");

    }

}
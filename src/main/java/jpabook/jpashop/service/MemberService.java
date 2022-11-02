package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//조회 성능 최적화(읽기는 true)
@RequiredArgsConstructor //final있는 필드만가지고 생성자를 만들어 줌
public class MemberService {

    //@Autowired //memberRepository주입
    private final MemberRepository memberRepository; //변경을 안 할 예정

    /*@Autowired //생성자에서 인젭션
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }*/

    //회원 가입
    @Transactional //따로 해주면 false
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //exception(중복회원 검증)
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //하나만 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    //회원 수정
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);

    }
}

package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getName())).collect(Collectors.toList());
        return new Result(collect);
    }

    @Data@AllArgsConstructor
    static class Result<T>{ //한번 감싸고 내보내기
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{ //이름만 반환
        private String name;
    }

    @PostMapping("/api/v1/members") //회원 등록 api
    public CreateMemberResponse savaMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members") //엔티티가 변경되어도 문제 없게 별도로 만들어 줌
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequst requst){
        Member member = new Member();
        member.setName(requst.getName());

        Long id = memberService.join(member);//아이디 반환
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequst {
        private  String name;
    }


    /**
     * 수정 API
     */
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }



    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}

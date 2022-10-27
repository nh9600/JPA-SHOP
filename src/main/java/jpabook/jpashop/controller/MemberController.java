package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm()); //넘어갈 때 데이터를 가지고 감
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){ //valid검증해줌, BindingResult 에러가 있으면 발생
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        if(result.hasErrors()){//에러인지
            return "members/createMemberForm"; //폼데이터를 가지고 가서 어떤 에러가 있는 화면에 보여줌
        }

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);//호출하면 저장 끝
        return "redirect:/"; //첫페이지로 돌아감
    }
}

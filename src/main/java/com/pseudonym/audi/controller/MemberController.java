package com.pseudonym.audi.controller;

import com.pseudonym.audi.domain.Member;
import com.pseudonym.audi.dto.MemberForm;
import com.pseudonym.audi.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping(value = "/member/new")
    public String create(MemberForm form) {

        Member member = new Member();


        member.setName(form.getName());
        member.setId(form.getId());
        member.setPw(form.getPw());
        member.setEmail(form.getEmail());
        member.setPhone(form.getPhone());
        member.setDepartment(form.getDepartment());
        member.setRole(form.getRole());

        memberService.join(member);

        return "redirect:/";
    }

    @PostMapping(value = "/member/login")
    public String login(@RequestParam("id") String id, @RequestParam("pw") String pw) {

        Member member = new Member();
        member.setId(id);
        member.setPw(pw);

        memberService.checkLogin(member);

        return "/main";
    }
}

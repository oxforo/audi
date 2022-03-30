package com.pseudonym.audi.service;

import com.pseudonym.audi.Repository.MemberRepository;
import com.pseudonym.audi.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public String join(Member member){

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getId())
                .ifPresent(m-> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public String checkLogin(Member member) {

        isContainMember(member);
        isCorrectPw(member);
        return member.getId();
    };

    private void isContainMember(Member member){
        if(memberRepository.findById(member.getId()).isEmpty()){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }
    private void isCorrectPw(Member member) {
        if(!memberRepository.findById(member.getId()).get().getPw().equals(member.getPw()) ){
            System.out.println("request Pw : "+ member.getPw());
            System.out.println("real Pw : "+ memberRepository.findById(member.getId()).get().getPw() );
            throw new IllegalStateException("패스워드가 일치하지 않습니다.");
        }
    }
}

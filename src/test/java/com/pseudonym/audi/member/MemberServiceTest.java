package com.pseudonym.audi.member;

import com.pseudonym.audi.Repository.MemoryMemberRepository;
import com.pseudonym.audi.domain.Member;
import com.pseudonym.audi.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join_OK() {
        //Given
        Member member = new Member();
        member.setId("user1");
        member.setName("name1");

        //where
        String saveId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    void duplication_join_FAIL() {
        //Given
        Member member = new Member();
        member.setId("user1");
        member.setName("name1");

        //where
        String saveId = memberService.join(member);

        //Then
        Assertions.assertThrows(IllegalStateException.class, ()-> {
            memberService.join(member);
        });
    }

    @Test
    void login_OK() {
        //Given
        Member member = new Member();
        member.setId("user1");
        member.setPw("pw1");
        member.setName("name1");
        memberService.join(member);

        //where
        String returnId = memberService.checkLogin(member);

        //Then
        assertEquals(member.getId(), returnId);
    }

    @Test
    void nonExistId_login_FAIL() {
        //Given
        Member member1 = new Member();
        member1.setId("user1");
        member1.setPw("pw1");
        member1.setName("name1");
        memberService.join(member1);

        Member member2 = new Member();
        member1.setId("user2");
        member1.setPw("pw2");
        member1.setName("name2");

        //where
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.checkLogin(member2));

        //Then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }

    @Test
    void incorrectPw_login_FAIL() {
        //Given
        Member member1 = new Member();
        member1.setId("user1");
        member1.setPw("pw1");
        member1.setName("name1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setId("user1");
        member2.setPw("pw2");

        //where
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.checkLogin(member2));

        //Then
        assertThat(e.getMessage()).isEqualTo("패스워드가 일치하지 않습니다.");
    }
}
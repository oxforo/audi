package com.pseudonym.audi.Repository;

import com.pseudonym.audi.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(String id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
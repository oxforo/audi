package com.pseudonym.audi.repository;

import com.pseudonym.audi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByIdAndEmail(String id, String email);
    Optional<User> findByNameAndEmail(String name, String email);
    List<User> findAll();

}

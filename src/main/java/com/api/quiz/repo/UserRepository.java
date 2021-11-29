package com.api.quiz.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.api.quiz.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByUsername(String username);

    @Override
    List<User> findAllById(Iterable<Integer> integers);

    User findByUsernameAndEmail(String username, String email);
}

package com.api.quiz.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.api.quiz.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByUsername(String username);




    User findByUsernameAndEmail(String username, String email);
}

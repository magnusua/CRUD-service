package com.example.pharmacy.repository;

import com.example.pharmacy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByLogin(String login);
}

package com.example.se_backend.dao;

import com.example.se_backend.entity.UserBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserBasicEntity, Integer> {
    UserBasicEntity findByName(String name);
}

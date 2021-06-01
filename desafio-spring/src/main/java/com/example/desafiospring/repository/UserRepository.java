package com.example.desafiospring.repository;

import com.example.desafiospring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
}

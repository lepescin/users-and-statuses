package com.lepescin.usersandstatuses.repository;

import com.lepescin.usersandstatuses.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}

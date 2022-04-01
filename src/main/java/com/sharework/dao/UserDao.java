package com.sharework.dao;

import com.sharework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);

    User getUserByPhoneNumber(String phoneNumber);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.jwt = ?1 WHERE u.id = ?2")
    void changeJwt(String refreshToken, long userid);

}


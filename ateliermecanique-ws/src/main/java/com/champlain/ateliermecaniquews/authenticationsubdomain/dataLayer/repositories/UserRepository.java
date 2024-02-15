package com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRoles_Name(String roleName);

    User findUserByEmail(String email);
    User findUserByUserIdentifier_UserId(String userId);
    Boolean existsByEmail(String email);
}

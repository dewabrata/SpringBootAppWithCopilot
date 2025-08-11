package com.erdjbe26.webservice.repository;

import com.erdjbe26.webservice.entity.MstUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MstUserRepository extends JpaRepository<MstUser, Long> {
    Optional<MstUser> findByUsername(String username);
    Optional<MstUser> findByEmail(String email);

    Optional<MstUser> findByUsernameOrEmail(String username, String email);
}

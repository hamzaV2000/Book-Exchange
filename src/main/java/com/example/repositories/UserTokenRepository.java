package com.example.repositories;

import com.example.entity.User;
import com.example.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    UserToken findUserTokenByUserName(String userName);

}

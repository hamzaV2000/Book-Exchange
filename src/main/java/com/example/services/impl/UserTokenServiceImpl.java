package com.example.services.impl;

import com.example.entity.User;
import com.example.entity.UserToken;
import com.example.repositories.UserTokenRepository;
import com.example.services.UserTokenService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserTokenServiceImpl implements UserTokenService {
    private final UserTokenRepository userTokenRepository;

    public UserTokenServiceImpl(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public UserToken findUserTokenByUserName(String username) {
        return userTokenRepository.findUserTokenByUserName(username);
    }

    @Override
    public Set<UserToken> findAll() {
        return null;
    }

    @Override
    public UserToken findById(Long aLong) {
        return null;
    }

    @Override
    public UserToken save(UserToken object) {
        return userTokenRepository.save(object);
    }

    @Override
    public void delete(UserToken object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}

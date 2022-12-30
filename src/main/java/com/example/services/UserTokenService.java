package com.example.services;

import com.example.entity.User;
import com.example.entity.UserToken;

public interface UserTokenService extends CrudService<UserToken, Long>{
    UserToken findUserTokenByUserName(String userName);
}

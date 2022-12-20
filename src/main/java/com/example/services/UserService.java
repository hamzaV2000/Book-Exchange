package com.example.services;

import com.example.crm.CrmUser;
import com.example.entity.User;

public interface UserService extends CrudService<User, Long>{

    User findByUserName(String userName);

    void save(CrmUser theCrmUser);
}

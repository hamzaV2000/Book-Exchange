package com.example.services;

import com.example.entity.Role;

public interface RoleService extends CrudService<Role, Long>{
    Role findByRole(String role);
}

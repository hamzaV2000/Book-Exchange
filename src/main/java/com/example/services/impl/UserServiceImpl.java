package com.example.services.impl;

import com.example.crm.CrmUser;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;
import com.example.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Set<User> findAll() {
        HashSet<User> set = new HashSet<>();
        userRepository.findAll().forEach(set::add);

        return set;
    }

    @Override
    public User findById(Long aLong) {
        return userRepository.findById(aLong).orElse(null);
    }

    @Override
    public User save(User object) {
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        Role userRole = roleRepository.findByRole("ROLE_USER");
        object.setRoles(new HashSet<Role>(Collections.singletonList(userRole)));
        return userRepository.save(object);
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void save(CrmUser theCrmUser, User user) {

        if(user == null)
            user = new User();

        if(theCrmUser.getPassword() != null)
            user.setPassword(theCrmUser.getPassword());

        user.setLastLoginDate(LocalDate.now());

        if(user.getJoinDate() == null)
            user.setJoinDate(LocalDate.now());

        if(theCrmUser.getUserName() != null)
            user.setUserName(theCrmUser.getUserName());
        if(theCrmUser.getCity() != null)
            user.setCity(theCrmUser.getCity());
        if(theCrmUser.getLastName() != null)
            user.setLastName(theCrmUser.getLastName());
        if(theCrmUser.getFirstName() != null)
            user.setFirstName(theCrmUser.getFirstName());
        if(theCrmUser.getImage() != null)
            user.setProfileImageUrl(theCrmUser.getImage());

        user.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByRole("ROLE_USER"))));

        if(theCrmUser.getEmail() != null)
            user.setEmail(theCrmUser.getEmail());

        if(theCrmUser.getInterest() != null)
            user.setInterest(theCrmUser.getInterest());

        save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

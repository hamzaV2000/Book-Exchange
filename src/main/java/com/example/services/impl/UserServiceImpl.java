package com.example.services.impl;

import com.example.crm.CrmUser;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;
import com.example.services.UserService;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
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
        object.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
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
    public void save(CrmUser theCrmUser) {
        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(theCrmUser.getPassword()));
        user.setLastLoginDate(LocalDate.now());
        user.setJoinDate(LocalDate.now());
        user.setUserName(theCrmUser.getUserName());
        user.setBirthDate(theCrmUser.getBirthDate());
       // user.setGender(theCrmUser.getGender());
       // user.setCity(theCrmUser.getCity());
      //  user.setTelephone(theCrmUser.getTelephone());
        //user.setStreetAddress(theCrmUser.getStreetAddress());
        user.setLastName(theCrmUser.getLastName());
        user.setFirstName(theCrmUser.getFirstName());
        user.setProfileImageUrl(theCrmUser.getImage());
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ROLE_USER"))));
        save(user);
    }
}

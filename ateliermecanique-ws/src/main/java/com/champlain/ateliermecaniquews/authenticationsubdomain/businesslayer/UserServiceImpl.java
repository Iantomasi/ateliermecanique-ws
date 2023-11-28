package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer.Role;
import com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer.RoleRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.RegisterDTO;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();



    @Override
    public User saveNewUser(RegisterDTO registerDTO) {
        Role role = roleRepository.findByRole("CUSTOMER"); //save new accounts as customer accounts

        User user = new User();
        user.setEmail(registerDTO.getEmail_id());
        user.setName(registerDTO.getName());
        user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}

package com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;


import lombok.*;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private UserIdentifier userIdentifier;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "cust_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
    Set<Role> roles = new HashSet<Role>();

    public Set<Role> getRole() {
        return roles;
    }

    public void setRole(Role role) {
        this.roles.add(role);
    }

}

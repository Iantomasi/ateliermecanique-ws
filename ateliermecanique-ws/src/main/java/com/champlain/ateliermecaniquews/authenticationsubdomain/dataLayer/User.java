package com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public User(String firstName, String lastName, String phoneNumber, String email, String password, Set<Role> roles,String picture) {
        this.userIdentifier = new UserIdentifier();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.picture = picture;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private UserIdentifier userIdentifier;

    @Size(max = 20)
    private String firstName;

    @Size(max = 40)
    private String lastName;

    @Size(max = 20)
    private String phoneNumber;


    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 200)
    private String picture;

    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}

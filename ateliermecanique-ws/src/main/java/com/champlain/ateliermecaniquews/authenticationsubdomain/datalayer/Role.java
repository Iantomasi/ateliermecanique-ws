package com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer;

import jakarta.persistence.*;


import lombok.*;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String role;

}

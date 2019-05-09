package com.iberifest.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role")
public class User_role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;


}

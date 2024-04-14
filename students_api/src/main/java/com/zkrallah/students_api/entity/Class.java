package com.zkrallah.students_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "classes")
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "requestedClass")
    private Set<Request> requests = new HashSet<>();
}

package com.zkrallah.students_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "class_id"})
})
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Class requestedClass;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Timestamp timestamp;
}

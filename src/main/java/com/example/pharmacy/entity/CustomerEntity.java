package com.example.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "inn")
    private Long inn;

    @Column(name = "familyStatus")
    private int familyStatus;

    @Column(name = "education")
    private int education;

    @Column(name = "segment")
    private int segment;

    @Column(name = "customerFamilyName")
    private int customerFamily;

}

package com.example.pharmacy.data.entity;

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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "inn")
    private String inn;

    @Column(name = "familystatus")
    private String familyStatus;

    @Column(name = "education")
    private String education;

    @Column(name = "segment")
    private String segment;

    @Column(name = "customerfamilyname")
    private String customerFamily;

}

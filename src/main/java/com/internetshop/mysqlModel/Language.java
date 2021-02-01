package com.internetshop.mysqlModel;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "language_name", nullable = false)
    private String name;
}

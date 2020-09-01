//package ru.otus.homework11.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Data
//@EqualsAndHashCode(of = {"id"})
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "authors")
//public class Author {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    public Author(String name) {
//        this.name = name;
//    }
//}

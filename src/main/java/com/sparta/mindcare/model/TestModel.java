package com.sparta.mindcare.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class TestModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String test;
}

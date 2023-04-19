package com.example.player.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.util.Objects;

public class Player {
    @Id
    private Long id;
    private String name;
    private Integer age;
    private String club;
    private String nationality;


    private LocalDate birthDate;
    public Player(Long id, String name, Integer age, String club, String nationality, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.club = club;
        this.nationality = nationality;
        this.birthDate = birthDate;
    }

    public Player(PlayerInput playerInput) {
        if(playerInput.getId()!= null )this.id = playerInput.getId();
        this.name = Objects.requireNonNull(playerInput.getName());
        this.age = playerInput.getAge();
        this.club = Objects.requireNonNull(playerInput.getClub());
        this.nationality = Objects.requireNonNull(playerInput.getNationality());
        this.birthDate = playerInput.getBirthDate();
    }

    public Player() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}

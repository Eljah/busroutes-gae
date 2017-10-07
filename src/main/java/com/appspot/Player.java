package com.appspot;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String name;
    public String rank;

    public Player() {
    }

    public Player(String name, String rank) {
        super();
        this.name = name;
        this.rank = rank;
    }

    // setters and getters skipped
}
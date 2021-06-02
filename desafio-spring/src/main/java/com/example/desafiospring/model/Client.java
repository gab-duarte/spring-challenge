package com.example.desafiospring.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "C")
public class Client extends User {

    public Client(String userName, String email) {
        super(userName, email);
    }

    public Client() {

    }
}

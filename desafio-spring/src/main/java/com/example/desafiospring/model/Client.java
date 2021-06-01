package com.example.desafiospring.model;

import com.example.desafiospring.dto.SellerDTO;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "C")
public class Client extends User {

    @ManyToMany
    private List<Seller> sellersFollowed = new ArrayList<>();

    public Client(String userName, String email) {
        super(userName, email);
    }

    public Client() {

    }

    public void addFollow(Seller sellerId){
        this.sellersFollowed.add(sellerId);
    }

    public void removeFollow(Seller sellerId){
        this.sellersFollowed.remove(sellerId);
    }
}

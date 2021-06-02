package com.example.desafiospring.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 1, discriminatorType = DiscriminatorType.STRING)
public abstract class User {

    @ManyToMany
    private List<Seller> followedSellers = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;

    @Column(insertable = false, updatable = false)
    private String type;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void addFollow(Seller sellerId){
        this.followedSellers.add(sellerId);
    }

    public void removeFollow(Seller sellerId){
        this.followedSellers.remove(sellerId);
    }

    public List<Seller> getFollowedSellers() {
        return followedSellers;
    }

    public boolean findSellerInFollowedList(Seller seller){
        return this.followedSellers.contains(seller);
    }
}

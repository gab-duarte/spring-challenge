package com.example.desafiospring.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "S")
public class Seller extends User{

    @ManyToMany
    private List<User> clientsFollowers = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<Post> posts = new ArrayList<>();

    public Seller(String userName, String email) {
        super(userName, email);
    }

    public Seller() {

    }

    public void addFollower(User user){
        this.clientsFollowers.add(user);
    }

    public void removeFollower(User user){
        this.clientsFollowers.remove(user);
    }

    public List<User> getClientsFollowers() {
        return clientsFollowers;
    }

    public void setClientsFollowers(List<User> clientsFollowers) {
        this.clientsFollowers = clientsFollowers;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}

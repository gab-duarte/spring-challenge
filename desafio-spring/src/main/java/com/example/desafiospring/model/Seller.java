package com.example.desafiospring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private List<User> followers = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    public Seller(String userName, String email) {
        super(userName, email);
    }

    public Seller() {

    }

    public void addFollower(User user){
        this.followers.add(user);
    }

    public void removeFollower(User user){
        this.followers.remove(user);
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}

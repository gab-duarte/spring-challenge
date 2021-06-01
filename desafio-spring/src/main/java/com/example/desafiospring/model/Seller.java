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
    private List<Client> clientsFollowers = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<Post> posts = new ArrayList<>();

    public Seller(String userName, String email) {
        super(userName, email);
    }

    public Seller() {

    }

    public void addFollower(Client client){
        this.clientsFollowers.add(client);
    }

    public void removeFollower(Client client){
        this.clientsFollowers.remove(client);
    }

    public List<Client> getClientsFollowers() {
        return clientsFollowers;
    }

    public void setClientsFollowers(List<Client> clientsFollowers) {
        this.clientsFollowers = clientsFollowers;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}

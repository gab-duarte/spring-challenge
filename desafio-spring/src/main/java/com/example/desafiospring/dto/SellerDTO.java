package com.example.desafiospring.dto;

import com.example.desafiospring.model.Seller;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SellerDTO {

    private Long id;
    private String userName;
    private Integer followers_count;
    private List<UserDTO> followers;

    public SellerDTO(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public SellerDTO(Seller seller, Integer followers_count) {
        this.id = seller.getId();
        this.userName = seller.getName();
        this.followers_count = followers_count;
    }

    public SellerDTO(Long id, String userName, List<UserDTO> followers) {
        this.id = id;
        this.userName = userName;
        this.followers = followers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(Integer followers_count) {
        this.followers_count = followers_count;
    }

    public List<UserDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserDTO> followers) {
        this.followers = followers;
    }
}

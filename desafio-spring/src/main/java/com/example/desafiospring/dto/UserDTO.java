package com.example.desafiospring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long userId;
    private String userName;
    private List<SellerDTO> followed;

    public UserDTO(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public UserDTO(Long userId, String userName, List<SellerDTO> followed){
        this.userId = userId;
        this.userName = userName;
        this.followed = followed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<SellerDTO> getFollowed() {
        return followed;
    }

    public void setFollowed(List<SellerDTO> followed) {
        this.followed = followed;
    }
}

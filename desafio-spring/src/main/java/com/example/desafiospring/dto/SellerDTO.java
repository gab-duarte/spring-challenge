package com.example.desafiospring.dto;

import com.example.desafiospring.model.Seller;

public class SellerDTO {

    private Long id;

    public SellerDTO(Seller seller) {
        this.id = seller.getId();
    }
}

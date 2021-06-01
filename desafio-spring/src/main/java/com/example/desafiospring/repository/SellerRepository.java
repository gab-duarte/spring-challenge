package com.example.desafiospring.repository;

import com.example.desafiospring.model.Seller;
import org.springframework.data.jpa.repository.Query;

public interface SellerRepository extends UserRepository<Seller>{
    @Query("from Seller")
    Seller findSellerById();
}

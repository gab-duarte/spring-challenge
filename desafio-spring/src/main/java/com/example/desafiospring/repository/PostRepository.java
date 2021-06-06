package com.example.desafiospring.repository;

import com.example.desafiospring.model.Post;
import com.example.desafiospring.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.seller.id = :seller order by p.date")
    List<Post> getPostsBySeller(@Param("seller") Long sellerId);
}

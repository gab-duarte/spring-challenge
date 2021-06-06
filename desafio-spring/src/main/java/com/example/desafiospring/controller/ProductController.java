package com.example.desafiospring.controller;

import com.example.desafiospring.dto.PostDTO;
import com.example.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/newpost")
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDTO postDTO){
        return productService.savePost(postDTO, false);
    }

    @GetMapping(path = "/followed/{userId}/list")
    public ResponseEntity<?> postList(@PathVariable Long userId, @RequestParam(required = false) String order){
        return productService.getPostsFromUserFollowedList(userId, order);
    }

    @PostMapping(path = "/newpromopost")
    public ResponseEntity<?> savePromoPost(@Valid @RequestBody PostDTO postDTO){
        return productService.savePost(postDTO, true);
    }

    @GetMapping(path = "/{userId}/countPromo")
    public ResponseEntity<?> countPromoPost(@PathVariable Long userId){
        return productService.getCountPromoPost(userId);
    }

    @GetMapping(path = "/{userId}/list")
    public ResponseEntity<?> promoProducts(@PathVariable Long userId){
        return productService.getPromoProductsFromSeller(userId);
    }

}

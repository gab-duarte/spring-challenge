package com.example.desafiospring.controller;

import com.example.desafiospring.dto.PostDTO;
import com.example.desafiospring.exception.PostInvalidException;
import com.example.desafiospring.exception.RequestParamInvalidException;
import com.example.desafiospring.exception.UserInvalidException;
import com.example.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/newpost")
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDTO postDTO) throws UserInvalidException, PostInvalidException {
        return productService.savePost(postDTO, false);
    }

    @GetMapping(path = "/followed/{userId}/list")
    public ResponseEntity<?> postList(@PathVariable Long userId, @RequestParam(required = false) String order) throws UserInvalidException, RequestParamInvalidException {
        return productService.getPostsFromUserFollowedList(userId, order);
    }

    @PostMapping(path = "/newpromopost")
    public ResponseEntity<?> savePromoPost(@Valid @RequestBody PostDTO postDTO) throws UserInvalidException, PostInvalidException {
        return productService.savePost(postDTO, true);
    }

    @GetMapping(path = "/{userId}/countPromo")
    public ResponseEntity<?> countPromoPost(@PathVariable Long userId) throws UserInvalidException {
        return productService.getCountPromoPost(userId);
    }

    @GetMapping(path = "/{userId}/list")
    public ResponseEntity<?> promoProducts(@PathVariable Long userId) throws UserInvalidException {
        return productService.getPromoProductsFromSeller(userId);
    }

}

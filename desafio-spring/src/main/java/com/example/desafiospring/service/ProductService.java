package com.example.desafiospring.service;

import com.example.desafiospring.dto.PostDTO;
import com.example.desafiospring.dto.PostsWithSellerDTO;
import com.example.desafiospring.dto.ProductDTO;
import com.example.desafiospring.exception.PostInvalidException;
import com.example.desafiospring.exception.RequestParamInvalidException;
import com.example.desafiospring.exception.UserInvalidException;
import com.example.desafiospring.model.Post;
import com.example.desafiospring.model.Product;
import com.example.desafiospring.model.Seller;
import com.example.desafiospring.model.User;
import com.example.desafiospring.repository.PostRepository;
import com.example.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UtilsService utilsService;

    public ResponseEntity<?> savePost(PostDTO postDTO, Boolean hasPromo) throws PostInvalidException, UserInvalidException {

        checkIfPromoPostIsValid(postDTO, hasPromo);
        Seller seller = utilsService.verifyIfSellerExists(postDTO.getUserId());
        Long postId = postDTO.getUserId();
        Product product = new Product(postDTO.getDetail().getProductName(), postDTO.getDetail().getType(), postDTO.getDetail().getBrand(), postDTO.getDetail().getColor(), postDTO.getDetail().getNotes());

        productRepository.save(product);
        if(hasPromo){
            postRepository.save(new Post(postId, seller, product, postDTO.getCategory(), postDTO.getPrice(), postDTO.getHasPromo(), postDTO.getDiscount()));
        }
        else{
            postRepository.save(new Post(postId, seller, product, postDTO.getCategory(), postDTO.getPrice()));
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<?> getPostsFromUserFollowedList(Long userId, String order) throws RequestParamInvalidException, UserInvalidException {

        User user = utilsService.verifyIfUserExists(userId);
        List<Seller> sellers = user.getFollowedSellers();
        List<Post> posts;
        List<Post> allPosts = new ArrayList<>();

        for (Seller seller: sellers){
            posts = postRepository.getPostsBySeller(seller.getId());
            allPosts.addAll(posts);
        }

        List<PostDTO> postDTOS = new ArrayList<>();

        if(order != null){
            checkRequestParam(order);
            Collections.sort(allPosts);
            if(order.equalsIgnoreCase("date_desc")){
                Collections.reverse(allPosts);
            }
        }

        for (Post post: allPosts){
            if(post.getDate().plusWeeks(2).isAfter(LocalDate.now()) || post.getDate().plusWeeks(2).isEqual(LocalDate.now())){
                ProductDTO productDTO = new ProductDTO(post.getProduct().getName(), post.getProduct().getType(), post.getProduct().getBrand(), post.getProduct().getColor(), post.getProduct().getNotes());
                postDTOS.add(new PostDTO(null, post.getPostId(), post.getDate(), productDTO, post.getCategory(), post.getPrice(), post.getHasPromo(), post.getDiscount()));
            }
        }
        PostsWithSellerDTO followedDTO = new PostsWithSellerDTO(userId, null, postDTOS);


        return new ResponseEntity<>(followedDTO, HttpStatus.OK);
    }

    public ResponseEntity<?> getCountPromoPost(Long sellerId) throws UserInvalidException {

        Seller seller = utilsService.verifyIfSellerExists(sellerId);
        long numberOfPromoPosts = seller.getPosts().stream().filter(p -> p.getHasPromo() != null && p.getHasPromo()).count();
        return new ResponseEntity<>(numberOfPromoPosts, HttpStatus.OK);
    }

    public ResponseEntity<?> getPromoProductsFromSeller(Long sellerId) throws UserInvalidException {

        Seller seller = utilsService.verifyIfSellerExists(sellerId);
        List<Post> posts = postRepository.getPostsBySeller(sellerId);
        posts = posts.stream().filter(p -> p.getHasPromo()!= null && p.getHasPromo()).collect(Collectors.toList());
        List<PostDTO> postDTOS = new ArrayList<>();
        ProductDTO productDTO;
        for(Post post: posts){
            productDTO = new ProductDTO(post.getProduct().getName(), post.getProduct().getType(), post.getProduct().getBrand(), post.getProduct().getColor(), post.getProduct().getNotes());
            postDTOS.add(new PostDTO(null, post.getPostId(), post.getDate(),productDTO, post.getCategory(), post.getPrice(), post.getHasPromo(), post.getDiscount()));
        }
        PostsWithSellerDTO postsWithSellerDTO = new PostsWithSellerDTO(sellerId, seller.getName(), postDTOS);

        return new ResponseEntity<>(postsWithSellerDTO, HttpStatus.OK);
    }

    private void checkRequestParam(String param) throws RequestParamInvalidException {
        if(!param.equalsIgnoreCase("date_asc") && !param.equalsIgnoreCase("date_desc")){
            throw new RequestParamInvalidException("Request param " + param);
        }
    }

    private void checkIfPromoPostIsValid(PostDTO postDTO, Boolean hasPromo) throws PostInvalidException {
        if(((postDTO.getHasPromo() == null || !postDTO.getHasPromo()) && hasPromo) || (postDTO.getHasPromo() != null && postDTO.getHasPromo() && !hasPromo)){
            throw new PostInvalidException("Post with id " + postDTO.getPost_id());
        }
    }

}
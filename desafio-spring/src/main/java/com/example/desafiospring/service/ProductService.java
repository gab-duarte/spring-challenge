package com.example.desafiospring.service;

import com.example.desafiospring.dto.PostsWithSellerDTO;
import com.example.desafiospring.dto.PostDTO;
import com.example.desafiospring.dto.ProductDTO;
import com.example.desafiospring.exception.PostInvalidException;
import com.example.desafiospring.exception.RequestParamInvalidException;
import com.example.desafiospring.exception.UserInvalidException;
import com.example.desafiospring.exception.UserNotFoundException;
import com.example.desafiospring.model.*;
import com.example.desafiospring.repository.ClientRepository;
import com.example.desafiospring.repository.PostRepository;
import com.example.desafiospring.repository.ProductRepository;
import com.example.desafiospring.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.desafiospring.service.UserService.handleException;


@Service
public class ProductService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<?> savePost(PostDTO postDTO, Boolean hasPromo){
        try {
            checkIfPromoPostIsValid(postDTO, hasPromo);
            Seller seller = verifyIfSellerExists(postDTO.getUserId());
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

        }catch (UserNotFoundException | PostInvalidException e){
            return handleException(e);
        }

    }

    public ResponseEntity<?> getPostsFromUserFollowedList(Long userId, String order){
        try{
            User user = verifyIfUserExists(userId);
            List<Seller> sellers = user.getFollowedSellers();
            List<Post> posts = null;
            List<Post> allPosts = new ArrayList<>();

            for (Seller seller: sellers){
                posts = postRepository.getPostsBySeller(seller.getId());
                allPosts.addAll(posts);
            }

            //allPosts = allPosts.stream().sorted(Comparator.comparing(Post::getDate)).collect(Collectors.toList());
            List<PostDTO> postDTOS = new ArrayList<>();

            if(order != null){
                checkRequestParam(order);
                Collections.sort(allPosts);
                if(order.equalsIgnoreCase("date_desc")){
                    Collections.reverse(allPosts);
                }
            }

            for (Post post: allPosts){
                if(post.getDate().plusWeeks(2).isAfter(LocalDate.now()) || post.getDate().plusWeeks(2).isEqual(LocalDate.now())){ // ARRUMAR CONDICAO
                    ProductDTO productDTO = new ProductDTO(post.getProduct().getName(), post.getProduct().getType(), post.getProduct().getBrand(), post.getProduct().getColor(), post.getProduct().getNotes());
                    postDTOS.add(new PostDTO(null, post.getPostId(), post.getDate(), productDTO, post.getCategory(), post.getPrice(), post.getHasPromo(), post.getDiscount()));
                }
            }
            PostsWithSellerDTO followedDTO = new PostsWithSellerDTO(userId, null, postDTOS);


            return new ResponseEntity<>(followedDTO, HttpStatus.OK);

        }catch (UserInvalidException | RequestParamInvalidException e){
            return handleException(e);
        }
    }

    public ResponseEntity<?> getCountPromoPost(Long sellerId){
        try{
            Seller seller = verifyIfSellerExists(sellerId);
            long numberOfPromoPosts = seller.getPosts().stream().filter(p -> p.getHasPromo() != null && p.getHasPromo()).count();
            return new ResponseEntity<>(numberOfPromoPosts, HttpStatus.OK);
        }catch (UserNotFoundException e){
            return handleException(e);
        }
    }

    public ResponseEntity<?> getPromoProductsFromSeller(Long sellerId){
        try {
            Seller seller = verifyIfSellerExists(sellerId);
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

        }catch (UserNotFoundException e){
            return handleException(e);
        }
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


    private Seller verifyIfSellerExists(Long sellerId) throws UserNotFoundException {
        if(sellerRepository.existsById(sellerId)){
            Optional<Seller> sellerO = sellerRepository.findById(sellerId);
            return sellerO.get();
        }
        else{
            throw new UserNotFoundException("Seller with id " + sellerId.toString());
        }
    }

    private User verifyIfUserExists(Long userId) throws UserInvalidException {

        if(clientRepository.existsById(userId)){
            Optional<Client> clientO = clientRepository.findById(userId);
            return clientO.get();
        }
        else if(sellerRepository.existsById(userId)){
            Optional<Seller> sellerO = sellerRepository.findById(userId);
            return sellerO.get();
        }
        else{
            throw new UserInvalidException("User with id "+ userId.toString());
        }
    }

}
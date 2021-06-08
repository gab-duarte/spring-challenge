package com.example.desafiospring.service;

import com.example.desafiospring.dto.SellerDTO;
import com.example.desafiospring.dto.UserDTO;
import com.example.desafiospring.exception.RequestParamInvalidException;
import com.example.desafiospring.exception.UserInvalidException;
import com.example.desafiospring.model.Client;
import com.example.desafiospring.model.Seller;
import com.example.desafiospring.model.User;
import com.example.desafiospring.repository.ClientRepository;
import com.example.desafiospring.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UtilsService utilsService;

    public ResponseEntity<?> userFollowSeller(Long userId, Long sellerId) throws UserInvalidException {

       User user = utilsService.verifyIfUserExists(userId);
       Seller userSeller = utilsService.verifyIfSellerExists(sellerId);

       if(!userFollowsSeller(user, userSeller)){
           user.addFollow(userSeller);
           userSeller.addFollower(user);
           saveUser(user);
           sellerRepository.save(userSeller);
       }

       return new ResponseEntity<>(HttpStatus.OK);
    }

    public  ResponseEntity<?> userUnfollowSeller(Long userId, Long sellerId) throws UserInvalidException {

        User user = utilsService.verifyIfUserExists(userId);
        Seller userSeller = utilsService.verifyIfSellerExists(sellerId);

        if(userFollowsSeller(user, userSeller)){
            user.removeFollow(userSeller);
            userSeller.removeFollower(user);
            saveUser(user);
            sellerRepository.save(userSeller);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> numberOfSellerFollowers(Long sellerId) throws UserInvalidException {

        Seller seller = utilsService.verifyIfSellerExists(sellerId);

        SellerDTO sellerDTO = new SellerDTO(seller, seller.getFollowers().size());

        return new ResponseEntity<>(sellerDTO, HttpStatus.OK);
    }

    public ResponseEntity<?> getFollowersList(Long sellerId, String order) throws UserInvalidException, RequestParamInvalidException {

        Seller seller = utilsService.verifyIfSellerExists(sellerId);
        List<User> followers = seller.getFollowers();

        if(order != null){
            checkRequestParam(order);
            Collections.sort(followers);
            if(order.equalsIgnoreCase("name_desc")){
                Collections.reverse(followers);
            }
        }

        List<UserDTO> followersDTO = new ArrayList<>();

        for(User user: followers){
            followersDTO.add(new UserDTO(user.getId(), user.getName()));
        }
        return new ResponseEntity<>(new SellerDTO(seller.getId(), seller.getName(), followersDTO), HttpStatus.OK);
    }

    public ResponseEntity<?> getSellersFollowedByUsers(Long userId) throws UserInvalidException {

        User user = utilsService.verifyIfUserExists(userId);
        List<Seller> sellersFollowed = user.getFollowedSellers();
        List<SellerDTO> followed = new ArrayList<>();

        for(Seller seller: sellersFollowed){
            followed.add(new SellerDTO(seller.getId(), seller.getName()));
        }

        return new ResponseEntity<>(new UserDTO(user.getId(), user.getName(), followed), HttpStatus.OK);

    }

    private void saveUser(User user){
        if(user.getType().equals("C")){
            Client client = (Client) user;
            clientRepository.save(client);
        }
        else {
            Seller seller = (Seller) user;
            sellerRepository.save(seller);
        }
    }

    private void checkRequestParam(String param) throws RequestParamInvalidException {
        if(!param.equalsIgnoreCase("name_asc") && !param.equalsIgnoreCase("name_desc")){
            throw new RequestParamInvalidException("Request param " + param);
        }
    }

    private boolean userFollowsSeller(User user, Seller seller){
        return user.findSellerInFollowedList(seller);
    }

}

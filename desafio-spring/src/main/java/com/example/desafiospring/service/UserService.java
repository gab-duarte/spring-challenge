package com.example.desafiospring.service;

import com.example.desafiospring.dto.ErrorDTO;
import com.example.desafiospring.dto.SellerDTO;
import com.example.desafiospring.dto.UserDTO;
import com.example.desafiospring.exception.PostInvalidException;
import com.example.desafiospring.exception.RequestParamInvalidException;
import com.example.desafiospring.exception.UserInvalidException;
import com.example.desafiospring.exception.UserNotFoundException;
import com.example.desafiospring.model.Client;
import com.example.desafiospring.model.Seller;
import com.example.desafiospring.model.User;
import com.example.desafiospring.repository.ClientRepository;
import com.example.desafiospring.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public  ResponseEntity<?> userUnfollowSeller(Long userId, Long sellerId){
        try{
            User user = verifyIfUserExists(userId);
            Seller userSeller = verifyIfSellerExists(sellerId);

            if(userFollowsSeller(user, userSeller)){
                user.removeFollow(userSeller);
                userSeller.removeFollower(user);

                if(user.getType().equals("C")){
                    Client client = (Client) user;
                    clientRepository.save(client);
                }
                else {
                    Seller seller = (Seller) user;
                    sellerRepository.save(seller);
                }
                sellerRepository.save(userSeller);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        }catch (UserNotFoundException | UserInvalidException e){
            return handleException(e);
        }
    }

    public ResponseEntity<?> userFollowSeller(Long userId, Long sellerId) {

       try{
           User user = verifyIfUserExists(userId);
           Seller userSeller = verifyIfSellerExists(sellerId);

           if(!userFollowsSeller(user, userSeller)){
               user.addFollow(userSeller);
               userSeller.addFollower(user);

               if(user.getType().equals("C")){
                   Client client = (Client) user;
                   clientRepository.save(client);
               }
               else {
                   Seller seller = (Seller) user;
                   sellerRepository.save(seller);
               }
               sellerRepository.save(userSeller);
           }

           return new ResponseEntity<>(HttpStatus.OK);
       }catch (UserNotFoundException | UserInvalidException e){
           return handleException(e);
       }
    }

    public ResponseEntity<?> numberOfSellerFollowers(Long sellerId) {
        try{
            Seller seller = verifyIfSellerExists(sellerId);

            SellerDTO sellerDTO = new SellerDTO(seller, seller.getFollowers().size());

            return new ResponseEntity<>(sellerDTO, HttpStatus.OK);

        }catch (UserNotFoundException e){
            return handleException(e);
        }
    }

    public ResponseEntity<?> getFollowersList(Long sellerId, String order){
        try{
            Seller seller = verifyIfSellerExists(sellerId);
            List<User> followers = seller.getFollowers();
            List<UserDTO> followersDTO = new ArrayList<>();

            if(order != null){
                checkRequestParam(order);
                Collections.sort(followers);
                if(order.equalsIgnoreCase("name_desc")){
                    Collections.reverse(followers);
                }
            }

            for(User user: followers){
                followersDTO.add(new UserDTO(user.getId(), user.getName()));
            }
            return new ResponseEntity<>(new SellerDTO(seller.getId(), seller.getName(), followersDTO), HttpStatus.OK);

        }catch (UserNotFoundException | RequestParamInvalidException e){
            return handleException(e);
        }
    }

    public ResponseEntity<?> getSellersFollowedByUsers(Long userId){
        try{
            User user = verifyIfUserExists(userId);
            List<Seller> sellersFollowed = user.getFollowedSellers();
            List<SellerDTO> followed = new ArrayList<>();

            for(Seller seller: sellersFollowed){
                followed.add(new SellerDTO(seller.getId(), seller.getName()));
            }

            return new ResponseEntity<>(new UserDTO(user.getId(), user.getName(), followed), HttpStatus.OK);

        }catch (UserInvalidException | UserNotFoundException e){
            return handleException(e);
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

    private User verifyIfUserExists(Long userId) throws UserNotFoundException, UserInvalidException {

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

    private Seller verifyIfSellerExists(Long sellerId) throws UserNotFoundException{
        if(sellerRepository.existsById(sellerId)){
            Optional<Seller> sellerO = sellerRepository.findById(sellerId);
            return sellerO.get();
        }
        else{
            throw new UserNotFoundException("Seller with id " + sellerId.toString());
        }
    }


    @ExceptionHandler({UserNotFoundException.class, UserInvalidException.class, RequestParamInvalidException.class})
    public static ResponseEntity<ErrorDTO> handleException(Exception e){
        if(e.getClass().equals(UserNotFoundException.class)){
            ErrorDTO errorDTO = new ErrorDTO("User Not Found", e.getMessage() + " not found");

            return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
        }
        else if(e.getClass().equals(RequestParamInvalidException.class)){
            ErrorDTO errorDTO = new ErrorDTO("Request Param Invalid",  e.getMessage() + " is invalid.");
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        else if(e.getClass().equals(PostInvalidException.class)){
            ErrorDTO errorDTO = new ErrorDTO("Post Invalid",  e.getMessage() + " is invalid.");
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

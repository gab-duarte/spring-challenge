package com.example.desafiospring.service;

import com.example.desafiospring.dto.ErrorDTO;
import com.example.desafiospring.dto.SellerDTO;
import com.example.desafiospring.dto.UserDTO;
import com.example.desafiospring.exception.UserInvalidException;
import com.example.desafiospring.exception.UserNotFoundException;
import com.example.desafiospring.model.Client;
import com.example.desafiospring.model.Seller;
import com.example.desafiospring.model.User;
import com.example.desafiospring.repository.ClientRepository;
import com.example.desafiospring.repository.SellerRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public ResponseEntity<?> clientFollowSeller(Long userId, Long sellerId) {

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
    /*
    private MappingJacksonValue useJacksonFilter(String filterName, Set<String> attributes, Object value){
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept(attributes);

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter(filterName, simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }
    */
    public ResponseEntity<?> numberOfSellerFollowers(Long sellerId) {
        try{
            Seller seller = verifyIfSellerExists(sellerId);

            SellerDTO sellerDTO = new SellerDTO(seller, seller.getFollowers().size());

            /*
            Set<String> attributes = new HashSet<>();
            attributes.add("followers");

            MappingJacksonValue mappingJacksonValue = useJacksonFilter("sellerFilter", attributes, sellerDTO);
            */
            return new ResponseEntity<>(sellerDTO, HttpStatus.OK);

        }catch (UserNotFoundException e){
            return handleException(e);
        }
    }

    public ResponseEntity<?> getFollowersList(Long sellerId){
        try{
            Seller seller = verifyIfSellerExists(sellerId);
            List<User> followers = seller.getFollowers();
            List<UserDTO> followersDTO = new ArrayList<>();
            for(User user: followers){
                followersDTO.add(new UserDTO(user.getId(), user.getName()));
            }
            return new ResponseEntity<>(new SellerDTO(seller.getId(), seller.getName(), followersDTO), HttpStatus.OK);

        }catch (UserNotFoundException e){
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


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(Exception e){
        if(e.getClass().equals(UserNotFoundException.class)){
            ErrorDTO errorDTO = new ErrorDTO("User Not Found", e.getMessage() + " not found");

            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        else{
            ErrorDTO errorDTO = new ErrorDTO("User Invalid",  e.getMessage() + " is invalid.");
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

    }
}

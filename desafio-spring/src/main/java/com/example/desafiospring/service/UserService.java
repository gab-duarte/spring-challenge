package com.example.desafiospring.service;

import com.example.desafiospring.dto.ErrorDTO;
import com.example.desafiospring.exception.UserNotFoundException;
import com.example.desafiospring.model.Client;
import com.example.desafiospring.model.Seller;
import com.example.desafiospring.repository.ClientRepository;
import com.example.desafiospring.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public ResponseEntity<?> clientFollowSeller(Long clientId, Long sellerId) throws UserNotFoundException {

       try{
           Optional<Client> userClient = verifyIfClientExists(clientId);
           Optional<Seller> userSeller = verifyIfSellerExists(sellerId);
           userClient.get().addFollow(userSeller.get());
           userSeller.get().addFollower(userClient.get());

           clientRepository.save(userClient.get());
           sellerRepository.save(userSeller.get());

           return new ResponseEntity<>(HttpStatus.OK);
       }catch (UserNotFoundException e){
           return handleException(e);
       }

    }

    private Optional<Client> verifyIfClientExists(Long clientId) throws UserNotFoundException {
        Optional<Client> userO = clientRepository.findById(clientId);
        if (userO.isEmpty()){
            throw new UserNotFoundException(clientId.toString());
        }
        return userO;
    }

    private Optional<Seller> verifyIfSellerExists(Long sellerId) throws UserNotFoundException {
        Optional<Seller> userO = sellerRepository.findById(sellerId);
        if (userO.isEmpty()){
            throw new UserNotFoundException(sellerId.toString());
        }
        return userO;
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(Exception e){
        if(e.getClass().equals(UserNotFoundException.class)){
            ErrorDTO errorDTO = new ErrorDTO("User", "The User with id " + e.getMessage() + " is invalid.");

            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        else{
            ErrorDTO errorDTO = new ErrorDTO("URL Not Found", "The URL with key " + e.getMessage() + " not found.");
            return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
        }

    }
}

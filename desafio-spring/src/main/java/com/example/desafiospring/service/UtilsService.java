package com.example.desafiospring.service;

import com.example.desafiospring.exception.UserInvalidException;
import com.example.desafiospring.model.Client;
import com.example.desafiospring.model.Seller;
import com.example.desafiospring.model.User;
import com.example.desafiospring.repository.ClientRepository;
import com.example.desafiospring.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SellerRepository sellerRepository;

     protected User verifyIfUserExists(Long userId) throws UserInvalidException {

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

    protected Seller verifyIfSellerExists(Long sellerId) throws UserInvalidException{
        if(sellerRepository.existsById(sellerId)){
            Optional<Seller> sellerO = sellerRepository.findById(sellerId);
            return sellerO.get();
        }
        else{
            throw new UserInvalidException("Seller with id " + sellerId.toString());
        }
    }
}

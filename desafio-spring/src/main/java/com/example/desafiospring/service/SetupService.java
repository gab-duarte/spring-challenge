package com.example.desafiospring.service;

import com.example.desafiospring.model.Client;
import com.example.desafiospring.model.Seller;
import com.example.desafiospring.repository.ClientRepository;
import com.example.desafiospring.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SetupService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<?> setup(){
        Client client1 = new Client("Gabriel Duarte","gabriel.duarte@mercadolivre.com");
        Client client2 = new Client("Thais Duarte","thais.duarte@hotmail.com");
        Client client3 = new Client("Adelaide Duarte","ade.duarte@hotmail.com");

        clientRepository.save(client1);
        clientRepository.save(client2);
        clientRepository.save(client3);

        Seller seller1 = new Seller("Daniel Henrique", "daniel.empreiteiro@outlook.com");
        Seller seller2 = new Seller("Zé", "zé.dasuaesquina@outlook.com");
        Seller seller3 = new Seller("Tonho", "tonho.bardasuaesquina@outlook.com");

        sellerRepository.save(seller1);
        sellerRepository.save(seller2);
        sellerRepository.save(seller3);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

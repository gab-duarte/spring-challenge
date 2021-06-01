package com.example.desafiospring.repository;

import com.example.desafiospring.model.Client;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends UserRepository<Client> {

    @Query("from Client")
    Client findClientById();
}

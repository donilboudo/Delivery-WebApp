package com.ilboudofabrice.service.interfaces;

import java.util.List;

import com.ilboudofabrice.domain.Client;

/**
 * Created by filboudo on 2017-02-16.
 */
public interface ClientService {
    List<Client> getClients();

    void removeClient(String id);

    Client findClientById(String id);

    Client findClientByName(String name);

    void updateClient(Client client);

    void addClient(String name, String phone, String address, String country, String city);
}

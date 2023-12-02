package ru.nau.calcProjects.services;

import ru.nau.calcProjects.exception.ClientExistException;
import ru.nau.calcProjects.exception.ClientNotFoundException;
import ru.nau.calcProjects.models.Client;

import java.util.List;

public interface ClientService {

    Client createClient(Client client) throws ClientExistException;

    List<Client> findAll(String title);

    Client findById(Long id) throws ClientNotFoundException;

    Client editClient(Client client, Long id) throws ClientNotFoundException;

    void deleteById(Long id);

    Client findByTitle(String title) throws ClientNotFoundException;
}

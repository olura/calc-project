package ru.nau.calcProjects.services;

import org.springframework.transaction.annotation.Transactional;
import ru.nau.calcProjects.dto.ClientDto;
import ru.nau.calcProjects.exception.ClientExistException;
import ru.nau.calcProjects.exception.ClientNotFoundException;
import ru.nau.calcProjects.models.Client;

import java.util.Collection;
import java.util.List;

public interface ClientService {

    Client createClient(ClientDto client) throws ClientExistException;

    List<Client> findAll(String title);

    List<Client> findAllClientsByUser(String title);

    Client findById(Long id) throws ClientNotFoundException;

    Client editClient(ClientDto ClientDto, Long id) throws ClientNotFoundException;

    Client editAllFieldToClient(ClientDto clientDto, Long id) throws ClientNotFoundException;

    void deleteById(Long id);

}

package ru.nau.calcProjects.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nau.calcProjects.exception.ClientExistException;
import ru.nau.calcProjects.exception.ClientNotFoundException;
import ru.nau.calcProjects.models.Client;
import ru.nau.calcProjects.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Override
    public Client createClient(Client client) throws ClientExistException {
        Optional<Client> existClient = clientRepository.findByTitle(client.getTitle());
        if (existClient.isPresent()) {
            throw new ClientExistException("Клиент с таким названием уже существует");
        }
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Client> findAll(String title) {
        if (title != null) {
            return clientRepository.findByTitleContaining(title, Sort.by(Sort.Order.desc("creationDate")));
        } else {
            return clientRepository.findAll(Sort.by(Sort.Order.desc("creationDate")));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Client findById(Long id) throws ClientNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Клиент под номером " + id + " не найден"));
    }

    @Transactional
    @Override
    public Client editClient(Client client, Long id) throws ClientNotFoundException {
        Client editClient = findById(id);
        editClient.setTitle(client.getTitle());
        editClient.setComment(client.getComment());
        return clientRepository.save(editClient);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client findByTitle(String title) throws ClientNotFoundException {
        return clientRepository.findFirstByTitleContaining(title)
                .orElseThrow(() -> new ClientNotFoundException("Клиент с названием " + title + " не найден"));
    }
}

package ru.nau.calcProjects.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nau.calcProjects.dto.ClientDto;
import ru.nau.calcProjects.exception.ClientExistException;
import ru.nau.calcProjects.exception.ClientNotFoundException;
import ru.nau.calcProjects.models.Client;
import ru.nau.calcProjects.models.User;
import ru.nau.calcProjects.repositories.ClientRepository;
import ru.nau.calcProjects.repositories.UserRepository;
import ru.nau.calcProjects.security.CustomUserDetails;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Client createClient(ClientDto clientDto) throws ClientExistException {
        Optional<Client> existClient = clientRepository.findByTitle(clientDto.getTitle());
        if (existClient.isPresent()) {
            throw new ClientExistException("Клиент с таким названием уже существует");
        }
        Client client = new Client(clientDto);
        String username = getUser().getUsername();
        User user = userRepository.findByUsername(username).get();
        client.setOwner(user);
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

    @Override
    public List<Client> findAllClientsByUser(String title) {
        Long userId = getUser().getId();
        if (title != null) {
            return clientRepository.findByOwnerIdAndTitleContaining(
                    userId, title, Sort.by(Sort.Order.desc("creationDate")));
        } else {
            return clientRepository.findByOwnerId(userId, Sort.by(Sort.Order.desc("creationDate")));
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
    public Client editClient(ClientDto clientDto, Long id) throws ClientNotFoundException {
        Client editClient = findById(id);
        editClient.setTitle(clientDto.getTitle());
        editClient.setComment(clientDto.getComment());
        return clientRepository.save(editClient);
    }

    @Transactional
    @Override
    public Client editAllFieldToClient(ClientDto clientDto, Long id) throws ClientNotFoundException {
        Client editClient = findById(id);
        User owner = userRepository.findByUsername(clientDto.getOwner())
                .orElseThrow(() -> new ClientNotFoundException("Клиент с именем " + clientDto.getOwner() + " не найден"));
        editClient.setOwner(owner);
        editClient.setTitle(clientDto.getTitle());
        editClient.setComment(clientDto.getComment());
        return clientRepository.save(editClient);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUser();
    }
}

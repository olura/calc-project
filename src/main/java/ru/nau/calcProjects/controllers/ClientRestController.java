package ru.nau.calcProjects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nau.calcProjects.exception.ClientExistException;
import ru.nau.calcProjects.exception.ClientNotFoundException;
import ru.nau.calcProjects.exception.ValidateException;
import ru.nau.calcProjects.models.Client;
import ru.nau.calcProjects.services.ClientService;

import java.util.List;

@RestController
public class ClientRestController {

    private final ClientService clientService;

    @Autowired
    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/api/client")
    public List<Client> getAllClients(@RequestParam(value = "title", required = false) String title) {
        return clientService.findAll(title);
    }

    @GetMapping("/api/client/findByTitle")
    public Client getClientByTitle(@RequestParam(value = "title") String title) throws ClientNotFoundException {
        return clientService.findByTitle(title);
    }

    @GetMapping ("/api/client/{id}")
    public Client getClient(@PathVariable long id) throws ClientNotFoundException {
        return clientService.findById(id);
    }

    @PostMapping("/api/client")
    public Client createClient(@RequestBody Client client) throws ClientExistException, ValidateException {
        if (client.getTitle().isEmpty()) {
            throw new ValidateException("Название клиента не может быть пустым. Необходимо заполнить.");
        }
        return clientService.createClient(client);
    }

    @PutMapping("/api/client/{id}")
    public Client editClient(@PathVariable("id") long id, @RequestBody Client client) throws ClientNotFoundException, ValidateException {
        if (client.getTitle().isEmpty()) {
            throw new ValidateException("Название клиента не может быть пустым. Необходимо заполнить.");
        }
        return clientService.editClient(client, id);
    }

    @DeleteMapping("/api/client/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        clientService.deleteById(id);
        return "{\"state\":\"success\"}";
    }
}

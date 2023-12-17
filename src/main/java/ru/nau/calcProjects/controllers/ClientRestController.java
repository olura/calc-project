package ru.nau.calcProjects.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nau.calcProjects.dto.ClientDto;
import ru.nau.calcProjects.exception.ClientExistException;
import ru.nau.calcProjects.exception.ClientNotFoundException;
import ru.nau.calcProjects.services.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
public class ClientRestController {

    private final ClientService clientService;

    @Autowired
    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/api/client")
    public List<ClientDto> getAllClientsByUser(@RequestParam(value = "title", required = false) String title) {
        return clientService.findAllClientsByUser(title)
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());
    }
    @GetMapping("/api/admin/client")
    public List<ClientDto> getAllClients(@RequestParam(value = "title", required = false) String title) {
        return clientService.findAll(title)
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping ("/api/client/{id}")
    public ClientDto getClient(@PathVariable @Positive long id) throws ClientNotFoundException {
        return new ClientDto(clientService.findById(id));
    }

    @PostMapping("/api/client")
    public ClientDto createClient(@Valid @RequestBody ClientDto client) throws ClientExistException {
        return new ClientDto(clientService.createClient(client));
    }

    @PutMapping("/api/client/{id}")
    public ClientDto editClient(@PathVariable("id") long id, @Valid @RequestBody ClientDto client) throws ClientNotFoundException {
        return new ClientDto(clientService.editClient(client, id));
    }

    @PutMapping("/api/admin/client/{id}")
    public ClientDto editAdminClient(@PathVariable("id") long id, @Valid @RequestBody ClientDto client) throws ClientNotFoundException {
        return new ClientDto(clientService.editAllFieldToClient(client, id));
    }

    @DeleteMapping("/api/client/{id}")
    public String deleteBook(@PathVariable("id") @Positive long id) {
        clientService.deleteById(id);
        return "{\"state\":\"success\"}";
    }
}

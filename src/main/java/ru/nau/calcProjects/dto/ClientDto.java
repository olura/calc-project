package ru.nau.calcProjects.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nau.calcProjects.models.Client;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class ClientDto {

    private Long id;

    private String owner;

    @Size(min = 3, max = 25, message = "Имя должно содержать от 3 до 25 символов")
    private String title;

    private String comment;

    private ZonedDateTime creationDate;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.owner = client.getOwner().getUsername();
        this.title = client.getTitle();
        this.comment = client.getComment();
        this.creationDate = client.getCreationDate();
    }
}

package ru.nau.calcProjects.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nau.calcProjects.dto.ClientDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
@Entity
@Getter
@Setter
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    private String title;

    private String comment;

    private ZonedDateTime creationDate;

    public Client() {
        this.creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow"));
    }

    public Client(String title){
        this.title = title;
        this.creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow"));
    }

    public Client(ClientDto clientDto) {
        this.title = clientDto.getTitle();
        this.comment = clientDto.getComment();
        this.creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow"));
    }
}

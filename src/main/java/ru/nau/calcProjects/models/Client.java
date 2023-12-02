package ru.nau.calcProjects.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}

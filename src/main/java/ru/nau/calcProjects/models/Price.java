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
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private ZonedDateTime creationDate;

    private Double licPercent;

    private Double workPercent;

    private Double hourCost;

    private boolean status = false;

    public Price() {
        this.creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow"));
    }

    public Price(String title, Double licPercent, Double workPercent, Double hourCost) {
        this.title = title;
        this.creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow"));
        this.licPercent = licPercent;
        this.workPercent = workPercent;
        this.hourCost = hourCost;
    }

    public Price(String title) {
        this.title = title;
        this.creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow"));
    }
}

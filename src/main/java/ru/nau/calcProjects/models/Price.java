package ru.nau.calcProjects.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @Size(min = 3, max = 25, message = "Название должно содержать от 3 до 25 символов")
    private String title;

    private ZonedDateTime creationDate;

    @NotNull
    @Positive(message = "Процент от стоимости лицензий должен быть больше 0")
    private Double licPercent;

    @NotNull
    @Positive(message = "Процент от стоимости проектных работ проектных работ должен быть больше 0")
    private Double workPercent;

    @NotNull
    @Positive(message = "Ставка человеко-часа должна быть больше 0")
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

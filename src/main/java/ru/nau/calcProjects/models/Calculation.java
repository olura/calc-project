package ru.nau.calcProjects.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nau.calcProjects.dto.CalculationDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "calculations")
public class Calculation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    private Price price;

    private ZonedDateTime creationDate;

    private Double licCost;

    private Double workCost;

    private Integer hours;

    private Double resultCalculation;

    public Calculation() {
        this.creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow"));
    }

    public Calculation(User author, Client client, Price price, CalculationDto calculationDto, Double resultCalculation) {
        this.author = author;
        this.client = client;
        this.price = price;
        this.creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow"));
        this.licCost = calculationDto.getLicCost();
        this.workCost = calculationDto.getWorkCost();
        this.hours = calculationDto.getHours();
        this.resultCalculation = resultCalculation;
    }
}

package ru.nau.calcProjects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nau.calcProjects.models.Calculation;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class CalculationDto {

    private Long id;

    private String author;

    @NotBlank(message = "Клиент не может быть пустым")
    private String client;

    private String price;

    private ZonedDateTime creationDate;

    @NotNull
    @Positive(message = "Стоимость лицензии должна быть больше 0")
    private Double licCost;

    @NotNull
    @Positive(message = "Стоимость проектных работ должна быть больше 0")
    private Double workCost;

    @NotNull
    @Positive(message = "Количестов часов должно быть больше 0")
    private Integer hours;

    private Double resultCalculation;

    public CalculationDto(Calculation calculation) {
        this.id = calculation.getId();
        this.author = calculation.getAuthor().getUsername();
        this.client = calculation.getClient().getTitle();
        this.price = calculation.getPrice().getTitle();
        this.creationDate = calculation.getCreationDate();
        this.licCost = calculation.getLicCost();
        this.workCost = calculation.getWorkCost();
        this.hours = calculation.getHours();
        this.resultCalculation = calculation.getResultCalculation();
    }
}

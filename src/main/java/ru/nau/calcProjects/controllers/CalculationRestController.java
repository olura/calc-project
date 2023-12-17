package ru.nau.calcProjects.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nau.calcProjects.dto.CalculationDto;
import ru.nau.calcProjects.exception.CalculationNotFoundException;
import ru.nau.calcProjects.models.Calculation;
import ru.nau.calcProjects.services.CalculationService;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
public class CalculationRestController {

    private final CalculationService calculationService;

    @Autowired
    public CalculationRestController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping("/api/admin/calculation")
    public List<CalculationDto> findAllByClientId(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "client", required = false) @Positive Long clientId) {
        return calculationService.findAllUserCalculationByUsernameAndClientId(username, clientId)
                .stream()
                .map(CalculationDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/calculation")
    public List<CalculationDto> findAllUserCalculationByClientId(
            @RequestParam(value = "client", required = false) @Positive Long clientId) {
        return calculationService.findAllUserCalculationByClientId(clientId)
                .stream()
                .map(CalculationDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping ("/api/calculation/{id}")
    public CalculationDto getCalculation(@PathVariable @Positive Long id) throws CalculationNotFoundException {
        return new CalculationDto(calculationService.findById(id));
    }

    @PostMapping("/api/calculation")
    public CalculationDto createCalculation(@Valid @RequestBody CalculationDto calculation) {
        Calculation savedCalculation = calculationService.createCalculation(calculation);
        return new CalculationDto(savedCalculation);
    }

    @DeleteMapping("/api/calculation/{id}")
    public String deleteBook(@PathVariable("id") @Positive long id) throws CalculationNotFoundException {
        calculationService.deleteById(id);
        return "{\"state\":\"success\"}";
    }
}

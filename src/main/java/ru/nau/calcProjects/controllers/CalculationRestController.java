package ru.nau.calcProjects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nau.calcProjects.dto.CalculationDto;
import ru.nau.calcProjects.exception.CalculationNotFoundException;
import ru.nau.calcProjects.exception.ValidateException;
import ru.nau.calcProjects.models.Calculation;
import ru.nau.calcProjects.services.CalculationService;

import java.util.List;
import java.util.stream.Collectors;

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
            @RequestParam(value = "client", required = false) Long clientId) {
        return calculationService.findAllUserCalculationByUsernameAndClientId(username, clientId)
                .stream()
                .map(CalculationDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/calculation")
    public List<CalculationDto> findAllUserCalculationByClientId(@RequestParam(value = "client", required = false) Long clientId) {
        return calculationService.findAllUserCalculationByClientId(clientId)
                .stream()
                .map(CalculationDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping ("/api/calculation/{id}")
    public CalculationDto getCalculation(@PathVariable Long id) throws CalculationNotFoundException {
        return new CalculationDto(calculationService.findById(id));
    }

    @PostMapping("/api/calculation")
    public CalculationDto createCalculation(@RequestBody CalculationDto calculation) throws ValidateException {
        if (calculation.getClient().isEmpty()) {
            throw new ValidateException("Название клиента не может быть пустым. Необходимо заполнить.");
        } else if (calculation.getLicCost() == null) {
            throw new ValidateException("Стоимость лицензии не может быть пустой. Необходимо заполнить.");
        } else if (calculation.getWorkCost() == null) {
            throw new ValidateException("Стоимость проектных работ не может быть пустой. Необходимо заполнить.");
        } else if (calculation.getHours() == null) {
            throw new ValidateException("Количество часов не может быть пустым. Необходимо заполнить.");
        }
        Calculation savedCalculation = calculationService.createCalculation(calculation);
        return new CalculationDto(savedCalculation);
    }

    @DeleteMapping("/api/calculation/{id}")
    public String deleteBook(@PathVariable("id") long id) throws CalculationNotFoundException {
        calculationService.deleteById(id);
        return "{\"state\":\"success\"}";
    }
}

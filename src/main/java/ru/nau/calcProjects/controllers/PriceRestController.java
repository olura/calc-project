package ru.nau.calcProjects.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nau.calcProjects.exception.PriceNotFoundException;
import ru.nau.calcProjects.models.Price;
import ru.nau.calcProjects.services.PriceService;

import java.util.List;

@Validated
@RestController
public class PriceRestController {

    private final PriceService priceService;

    @Autowired
    public PriceRestController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/api/price")
    public List<Price> getAllPrices() {
        return priceService.findAll();
    }

    @GetMapping ("/api/price/{id}")
    public Price getPrice(@PathVariable @Positive long id) throws PriceNotFoundException {
        return priceService.findById(id);
    }

    @PostMapping("/api/price")
    public Price createPrice(@Valid @RequestBody Price price) {
        return priceService.createPrice(price);
    }

    @PutMapping("/api/price/{id}")
    public Price editPrice(@PathVariable("id") @Positive long id, @Valid @RequestBody Price price) throws PriceNotFoundException {
        return priceService.editPrice(price, id);
    }

    @DeleteMapping("/api/price/{id}")
    public String deleteBook(@PathVariable("id") @Positive long id) throws PriceNotFoundException {
        priceService.deleteById(id);
        return "{\"state\":\"success\"}";
    }
}

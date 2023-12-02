package ru.nau.calcProjects.services;

import ru.nau.calcProjects.exception.PriceNotFoundException;
import ru.nau.calcProjects.models.Price;

import java.util.List;

public interface PriceService {

    Price createPrice(Price price);

    List<Price> findAll();

    Price findById(Long id) throws PriceNotFoundException;

    Price editPrice(Price price, Long id) throws PriceNotFoundException;

    void deleteById(Long id) throws PriceNotFoundException;
}

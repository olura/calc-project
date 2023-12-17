package ru.nau.calcProjects.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nau.calcProjects.exception.NotActualPriceException;
import ru.nau.calcProjects.exception.PriceNotFoundException;
import ru.nau.calcProjects.models.Price;
import ru.nau.calcProjects.repositories.PriceRepository;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {
    protected final PriceRepository priceRepository;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Transactional
    private Price setNewActualPrice(Price price) {
        Price actualPrice = priceRepository.findByStatus(true);
        if (actualPrice != null) {
            actualPrice.setStatus(false);
            priceRepository.save(actualPrice);
        }
        price.setStatus(true);
        return price;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Price> findAll() {
        return priceRepository.findAll(Sort.by(
                Sort.Order.desc("creationDate"),
                Sort.Order.asc("status")
        ));
    }

    @Transactional(readOnly = true)
    @Override
    public Price findById(Long id) throws PriceNotFoundException {
        return priceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException("Прайс под номером " + id + " не найден"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws PriceNotFoundException {
        Price deletePrice = findById(id);
        priceRepository.deleteById(id);
        if (deletePrice.isStatus()) {
            Price actualPrice = priceRepository.findFirstByOrderByCreationDateDesc();
            setNewActualPrice(actualPrice);
        }
    }

    @Transactional
    @Override
    public Price createPrice(Price price) {
        Price actualPrice = setNewActualPrice(price);
        return priceRepository.save(actualPrice);
    }

    @Transactional
    @Override
    public Price editPrice(Price price, Long id) throws PriceNotFoundException, NotActualPriceException {
        Price editPrice = findById(id);
        editPrice.setTitle(price.getTitle());
        editPrice.setLicPercent(price.getLicPercent());
        editPrice.setWorkPercent(price.getWorkPercent());

        if (price.isStatus()) {
            setNewActualPrice(editPrice);
        } else {
            Price actualPrice = priceRepository.findByStatus(true);
            if (id.equals(actualPrice.getId())) {
                throw new NotActualPriceException("Невозможно сменить статус данного прайса");
            }
            editPrice.setStatus(price.isStatus());
        }
        return priceRepository.save(editPrice);
    }
}

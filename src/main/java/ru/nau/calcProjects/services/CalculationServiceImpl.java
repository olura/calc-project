package ru.nau.calcProjects.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nau.calcProjects.dto.CalculationDto;
import ru.nau.calcProjects.exception.CalculationNotFoundException;
import ru.nau.calcProjects.models.Calculation;
import ru.nau.calcProjects.models.Client;
import ru.nau.calcProjects.models.Price;
import ru.nau.calcProjects.models.User;
import ru.nau.calcProjects.repositories.CalculationRepository;
import ru.nau.calcProjects.repositories.ClientRepository;
import ru.nau.calcProjects.repositories.PriceRepository;
import ru.nau.calcProjects.repositories.UserRepository;
import ru.nau.calcProjects.security.CustomUserDetails;

import java.util.List;

@Service
public class CalculationServiceImpl implements CalculationService {

    private final CalculationRepository calculationRepository;
    private final PriceRepository priceRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;


    @Autowired
    public CalculationServiceImpl(CalculationRepository calculationRepository, PriceRepository priceRepository, UserRepository userRepository, ClientRepository clientRepository) {
        this.calculationRepository = calculationRepository;
        this.priceRepository = priceRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Override
    public Calculation createCalculation(CalculationDto calculationDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUser().getUsername();

        User user = userRepository.findByUsername(username).get();
        Client client = clientRepository.findByTitle(calculationDto.getClient()).get();
        Price actualPrice = priceRepository.findByStatus(true);
        Double result = (calculationDto.getLicCost() * actualPrice.getLicPercent() / 100)
                + (calculationDto.getWorkCost() * actualPrice.getWorkPercent() / 100)
                + calculationDto.getHours() * actualPrice.getHourCost();
        calculationDto.setResultCalculation(result);

        Calculation calculation = new Calculation(user, client, actualPrice, calculationDto, result);
        return calculationRepository.save(calculation);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Calculation> findAllByClientId(Long clientId) {
        if (clientId != null) {
            return calculationRepository
                .findTop100ByClientId(clientId, Sort.by(Sort.Order.desc("creationDate")));
        } else {
            return calculationRepository.findTop100ByOrderByCreationDateDesc();
        }
    }

    @Override
    public List<Calculation> findAllUserCalculationByClientId(Long clientId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long authorId = customUserDetails.getUser().getId();
        if (clientId != null) {
            return calculationRepository
                .findTop100ByAuthorIdAndClientId(authorId, clientId, Sort.by(Sort.Order.desc("creationDate")));
        } else {
            return calculationRepository.findTop100ByAuthorId(authorId, Sort.by(Sort.Order.desc("creationDate")));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Calculation findById(Long id) throws CalculationNotFoundException {
        return calculationRepository.findById(id)
                .orElseThrow(() -> new CalculationNotFoundException("Расчёт с " + id + " не найден"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws CalculationNotFoundException {
        calculationRepository.deleteById(id);
    }
}

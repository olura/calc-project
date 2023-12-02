package ru.nau.calcProjects.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nau.calcProjects.exception.UserExistException;
import ru.nau.calcProjects.exception.UserNotFoundException;
import ru.nau.calcProjects.models.Role;
import ru.nau.calcProjects.models.User;
import ru.nau.calcProjects.repositories.UserRepository;
import ru.nau.calcProjects.security.CustomUserDetails;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository  userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this. userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User serviceUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(serviceUser);
    }

    @Transactional(readOnly = true)
    public Optional <User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User addUser(User user) throws UserExistException {
        Optional<User> userFormDb = userRepository.findByUsername(user.getUsername());
        if (userFormDb.isPresent())
            throw new UserExistException ("Пользователь с таким логином уже существует!");
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAllUser () {
        return userRepository.findAll();
    }

    @Transactional
    public User editUser(User user, Long id) throws UserNotFoundException {
        User editUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь под номером " + id + " не найден"));

        String encodePassword = passwordEncoder.encode(user.getPassword());
        editUser.setPassword(encodePassword);
        editUser.setEmail(user.getEmail());
        return userRepository.save(editUser);
    }
    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}

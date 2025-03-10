package nnpia.component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nnpia.domain.Address;
import nnpia.domain.User;
import nnpia.repository.AddressRepository;
import nnpia.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j // logger
@AllArgsConstructor // lombok
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;


    @Override
    public void run(String... args) throws Exception {
        Address address = new Address("Dukelská 293", "Zásmuky", "281 44");
        User user = new User("admin@upce.cz", "Heslo123", address );

        if (!userRepository.existsByEmail(user.getEmail())) {
            log.debug("Admin user created: {}", user);
            userRepository.save(user);
        }
    }
}

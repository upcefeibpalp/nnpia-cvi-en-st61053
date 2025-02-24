package nnpia.component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nnpia.domain.Address;
import nnpia.domain.User;
import nnpia.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j // logger
@AllArgsConstructor // lombok
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        Address address = new Address(0L, "Dukelská", "Zásmuky", "293");
       User user = new User(0L, "admin@upce.cz", "Heslo123", address );

        if (!userRepository.existsById(user.getId())) {
            log.debug("Admin user created" + user);
            userRepository.save(user);
        }

       if (!userRepository.existsById(user.getId())) {
           log.debug("Admin user created" + user);
           userRepository.save(user);
       }

    }
}

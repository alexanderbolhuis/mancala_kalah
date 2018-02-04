package com.kalah;

import com.kalah.domain.Player;
import com.kalah.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Main class
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.kalah.domain"})
public class KalahApplication {

    public static void main(String[] args) {

        SpringApplication.run(KalahApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PlayerRepository playerRepository) {
        return (args) -> {

            // Create a couple of players
            playerRepository.save(new Player("alexander", "alexander@bolhuis.com", new BCryptPasswordEncoder().encode("alexander")));
            playerRepository.save(new Player("irene", "irene@vanderheijden.com",  new BCryptPasswordEncoder().encode("irene")));
            playerRepository.save(new Player("ralph", "ralph@ralph.com", new BCryptPasswordEncoder().encode("ralph")));
            playerRepository.save(new Player("bart", "bart@bart.com",  new BCryptPasswordEncoder().encode("bart")));
            playerRepository.save(new Player("thijmen", "thijmen@thijmen.com",  new BCryptPasswordEncoder().encode("thijmen")));
        };
    }

}
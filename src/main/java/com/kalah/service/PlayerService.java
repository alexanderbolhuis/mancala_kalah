package com.kalah.service;

import com.kalah.domain.Player;
import com.kalah.dto.PlayerDTO;
import com.kalah.repository.PlayerRepository;
import com.kalah.security.ContextUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(PlayerDTO playerDTO) {
        Player player = new Player(playerDTO.getUsername(), playerDTO.getEmail(), playerDTO.getPassword());
        playerRepository.save(player);

        return player;
    }

    public Player getPlayerByUsername(String name) {
        return playerRepository.findOneByUsername(name);
    }

    public Player getLoggedInUser() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return playerRepository.findOneByUsername(principal.getPlayer().getUsername());
    }
}

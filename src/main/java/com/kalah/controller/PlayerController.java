package com.kalah.controller;

import com.kalah.domain.Player;
import com.kalah.dto.PlayerDTO;
import com.kalah.security.ContextUser;
import com.kalah.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Player createAccount(@RequestBody PlayerDTO playerDTO) {
        Player newPlayer = playerService.createPlayer(playerDTO);
        return newPlayer;
    }

    @RequestMapping(value = "/logged", produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getLoggedInPlayer() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return playerService.getPlayerByUsername(principal.getPlayer().getUsername());
    }
}
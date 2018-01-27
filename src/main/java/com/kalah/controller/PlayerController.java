package com.kalah.controller;

import com.kalah.domain.Player;
import com.kalah.dto.PlayerDTO;
import com.kalah.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
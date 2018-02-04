package com.kalah.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Board domain class
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @OneToMany(mappedBy = "board")
    private List<Pit> pits;

    public Board(Game game) {
        this.game = game;
    }
}

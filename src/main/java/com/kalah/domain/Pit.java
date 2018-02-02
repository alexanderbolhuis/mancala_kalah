package com.kalah.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kalah.enums.GameState;
import com.kalah.enums.PitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Check(constraints = "pit_type = 'HOUSE' or pit_type = 'STORE'")
@NoArgsConstructor
@AllArgsConstructor
public class Pit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name = "position", nullable = false)
    private int position;

    @Column(name = "nr_of_stones", nullable = false)
    private int numberOfStones;

    @Enumerated(EnumType.STRING)
    private PitType pitType;

    public Pit(Board board, int position, int numberOfStones, PitType pitType) {
        this.board = board;
        this.position = position;
        this.numberOfStones = numberOfStones;
        this.pitType = pitType;
    }
}

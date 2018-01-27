package com.kalah.service;

import com.kalah.domain.Board;
import com.kalah.domain.Pit;
import com.kalah.enums.PitType;
import com.kalah.repository.PitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PitService {

    private final PitRepository pitRepository;

    @Autowired
    public PitService(PitRepository pitRepository) {
        this.pitRepository = pitRepository;
    }

    public Pit createPit(Board board, PitType pitType, int position, int nrOfStones) {
        Pit pit = new Pit();
        pit.setBoard(board);
        pit.setPitType(pitType);
        pit.setPosition(position);
        pit.setNumberOfStones(nrOfStones);

        pitRepository.save(pit);

        return pit;
    }

    public Pit updatePitNumberOfStones(Board board, int position, int nrOfStones) {
        Pit pit = getPitByBoardAndPosition(board, position);
        pit.setNumberOfStones(nrOfStones);

        pitRepository.save(pit);

        return pit;
    }

    public Pit getPitByBoardAndPosition(Board board, int position) {
        return pitRepository.findByBoardAndPosition();
    }

    public List<Pit> getPitsByBoard(Board board) {
        return pitRepository.findByBoard(board);
    }

    public List<Pit> getPitsByBoardOrderedPosition(Board board) {
        return pitRepository.findByBoard(board);
    }

}

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

    private PitRepository pitRepository;

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

    public Pit updatePitNumberOfStonesByAmount(Board board, int position, int amount) {
        Pit pit = getPitByBoardAndPosition(board, position);
        int currentAmount = pit.getNumberOfStones();
        int newAmount = currentAmount + amount;
        pit.setNumberOfStones(newAmount);

        pitRepository.save(pit);

        return pit;
    }

    public Pit updatePitNumberOfStonesByOne(Board board, int position) {
        Pit pit = getPitByBoardAndPosition(board, position);
        int currentAmount = pit.getNumberOfStones();
        currentAmount++;
        pit.setNumberOfStones(currentAmount);

        pitRepository.save(pit);

        return pit;
    }

    public int getPitNumberOfStonesByBoardAndPosition(Board board, int position) {
        Pit pit = getPitByBoardAndPosition(board, position);
        return pit.getNumberOfStones();
    }

    public Pit getPitByBoardAndPosition(Board board, int position) {
        return pitRepository.findByBoardAndPosition(board, position);
    }

    public List<Pit> getPitsByBoard(Board board) {
        return pitRepository.findByBoard(board);
    }

    public List<Pit> getPitsByBoardOrderedPosition(Board board) {
        return pitRepository.findByBoardOrderByPositionAsc(board);
    }

}

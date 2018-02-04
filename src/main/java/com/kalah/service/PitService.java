package com.kalah.service;

import com.kalah.domain.Board;
import com.kalah.domain.Pit;
import com.kalah.enums.PitType;
import com.kalah.repository.PitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class for @{@link Pit} related actions
 */
@Service
@Transactional
public class PitService {

    private PitRepository pitRepository;

    /**
     * PitService constructor
     *
     * @param pitRepository @{@link PitRepository} dependency
     */
    @Autowired
    public PitService(PitRepository pitRepository) {
        this.pitRepository = pitRepository;
    }

    /**
     * Function to create a Pit
     *
     * @param board @{@link Board} to create on
     * @param pitType @{@link PitType} of the Pit to create
     * @param position of the Pit
     * @param nrOfStones number of stones in the Pit
     * @return @{@link Pit} created
     */
    public Pit createPit(Board board, PitType pitType, int position, int nrOfStones) {
        // Create Pit
        Pit pit = new Pit(board, position, nrOfStones, pitType);

        // Save Pit
        pitRepository.save(pit);

        return pit;
    }

    /**
     * Function to update the number of stones on a Pit
     *
     * @param board @{@link Board} the Pit is on
     * @param position of the Pit
     * @param nrOfStones new number of stones in the Pit
     * @return @{@link Pit} that was updated
     */
    public Pit updatePitNumberOfStones(Board board, int position, int nrOfStones) {
        // Retrieve Pit
        Pit pit = getPitByBoardAndPosition(board, position);

        // Update Pit
        pit.setNumberOfStones(nrOfStones);

        // Save Pit
        pitRepository.save(pit);

        return pit;
    }

    /**
     * Function to update the number of stones on a Pit by a certain amount
     *
     * @param board @{@link Board} the Pit is on
     * @param position of the Pit
     * @param amount number of stones to add in the Pit
     * @return @{@link Pit} that was updated
     */
    public Pit updatePitNumberOfStonesByAmount(Board board, int position, int amount) {
        // Retrieve Pit
        Pit pit = getPitByBoardAndPosition(board, position);

        // Update Pit
        int currentAmount = pit.getNumberOfStones();
        int newAmount = currentAmount + amount;
        pit.setNumberOfStones(newAmount);

        // Save Pit
        pitRepository.save(pit);

        return pit;
    }

    /**
     * Function to update the number of stones on a Pit by one
     *
     * @param board @{@link Board} the Pit is on
     * @param position of the Pit
     * @return @{@link Pit} that was updated
     */
    public Pit updatePitNumberOfStonesByOne(Board board, int position) {
        // Retrieve Pit
        Pit pit = getPitByBoardAndPosition(board, position);

        // Update Pit
        int currentAmount = pit.getNumberOfStones();
        currentAmount++;
        pit.setNumberOfStones(currentAmount);

        // Save Pit
        pitRepository.save(pit);

        return pit;
    }

    /**
     * Function to get the number of stones by Board and position
     *
     * @param board @{@link Board} to get info from
     * @param position of the Pit to get info from
     * @return The number of stones on the Pit
     */
    public int getPitNumberOfStonesByBoardAndPosition(Board board, int position) {
        Pit pit = getPitByBoardAndPosition(board, position);
        return pit.getNumberOfStones();
    }

    /**
     * Function to retrieve a Pit by @{@link Board} and position
     *
     * @param board @{@link Board} to get Pit from
     * @param position of the Pit
     * @return @{@link Pit} matching params
     */
    public Pit getPitByBoardAndPosition(Board board, int position) {
        return pitRepository.findByBoardAndPosition(board, position);
    }
}

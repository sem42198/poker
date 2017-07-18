package model;

import java.util.Random;

/**
 * represents an AI player that has a chance of bluffing on a turn
 * @author smorrissey3
 * @version 1.0
 */
public class BluffingAI extends AIPlayer {

    private int bluffChance;
    private Random rand = new Random();

    /**
     * BluffingAI's constructor
     * @param  name  The name of the AI
     * @param  money The amount of money the AI starts with
     * @param bluffChance The chance that this AI will bluff
     */
    public BluffingAI(String name, int money, int bluffChance) {
        super(name, money);
        this.bluffChance = bluffChance;
    }

    /**
     * BluffingAI's constructor
     * @param  name  The name of the AI
     * @param  money The amount of money the AI starts with
     */
    public BluffingAI(String name, int money) {
        this(name, money, 45);
    }

    @Override
    public Turn getTurn(int minBet, int maxBet, Card[] boardCards,
        HandType hand) {
        if (rand.nextInt(100) < bluffChance) {
            return super.getTurn(minBet, maxBet, boardCards,
                HandType.ROYAL_FLUSH);
        } else {
            return super.getTurn(minBet, maxBet, boardCards, hand);
        }
    }

}
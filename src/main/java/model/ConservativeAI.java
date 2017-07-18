package model;

/**
 * represents an AI player that bets less than usual
 * does this by setting the starting value of perc, which is used to determine
 * turn to -10 rather than 0. Thus, the AI will bet as if their hand is a little
 * worse than it really is
 * @author smorrissey3
 * @version 1.0
 */
public class ConservativeAI extends AIPlayer {

    /**
     * ConservativeAI's constructor
     * @param  name  The name of the AI
     * @param  money The amount of money the AI starts with
     */
    public ConservativeAI(String name, int money) {
        super(name, money);
    }

    @Override
    public Turn getTurn(int minBet, int maxBet, Card[] boardCards,
        HandType hand) {
        return super.getTurn(minBet, maxBet, boardCards, hand, -10);
    }

}
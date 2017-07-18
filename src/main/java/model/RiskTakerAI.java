package model;

/**
 * represents an AI player that bets more than usual
 * does this by setting the starting value of perc, which is used to determine
 * turn to 20 rather than 0. Thus, the AI will bet as if their hand is somewhat
 * better than it actually is.
 * @author smorrissey3
 * @version 1.0
 */
public class RiskTakerAI extends AIPlayer {

    /**
     * RiskTakerAI's constructor
     * @param  name  The name of the AI
     * @param  money The amount of money the AI starts with
     */
    public RiskTakerAI(String name, int money) {
        super(name, money);
    }

    @Override
    public Turn getTurn(int minBet, int maxBet, Card[] boardCards,
        HandType hand) {
        return super.getTurn(minBet, maxBet, boardCards, hand, 20);
    }

}
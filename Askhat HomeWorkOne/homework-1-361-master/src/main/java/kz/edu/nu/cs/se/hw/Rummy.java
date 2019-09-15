package kz.edu.nu.cs.se.hw;

import java.util.*;

/**
 * Starter code for a class that implements the <code>PlayableRummy</code>
 * interface. A constructor signature has been added, and method stubs have been
 * generated automatically in eclipse.
 * <p>
 * Before coding you should verify that you are able to run the accompanying
 * JUnit test suite <code>TestRummyCode</code>. Most of the unit tests will fail
 * initially.
 *
 * @see PlayableRummy
 * //* @see TestRummyCode
 */
public class Rummy implements PlayableRummy {
    private String[] players;
    private boolean[] notRummy;
    private Steps step = Steps.WAITING;
    private Stack<String> deck;
    private List<LinkedList<String>> playerHand;
    private List<LinkedList<String>> melds;
    private Stack<String> discardPile;
    private int currentPlayer = 0;
    private String discardedCard = "";

    public Rummy(String... players) {
        if (players.length < 2) {
            throw new RummyException("Invalid players number", RummyException.NOT_ENOUGH_PLAYERS);
        } else if (players.length > 6) {
            throw new RummyException("Invalid players number", RummyException.EXPECTED_FEWER_PLAYERS);
        } else {
            this.players = players;
            notRummy = new boolean[players.length];
            deck = new Stack<>();
            discardPile = new Stack<>();
            playerHand = new ArrayList<>();
            melds = new LinkedList<>();
            for (int i = 0; i < players.length; i++) {
                playerHand.add(new LinkedList<>());
            }
            final String[] suits = new String[]{"C", "D", "H", "S", "M"};
            final String[] ranks = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
            for (String rank : ranks) {
                for (String suit : suits) {
                    deck.push(rank + suit);
                }
            }
        }
    }

    @Override
    public String[] getPlayers() {
        return players;
    }

    @Override
    public int getNumPlayers() {
        return players.length;
    }

    @Override
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public int getNumCardsInDeck() {
        return deck.size();
    }

    @Override
    public int getNumCardsInDiscardPile() {
        return discardPile.size();
    }

    @Override
    public String getTopCardOfDiscardPile() {
        if (discardPile.size() == 0) {
            throw new RummyException("Discard Pile is empty");
        }
        return discardPile.pop();
    }

    @Override
    public String[] getHandOfPlayer(int player) {
        if (player > players.length - 1 || player < 0) {
            throw new RummyException("Invalid reference to player", RummyException.NOT_VALID_INDEX_OF_PLAYER);
        }
        return playerHand.get(player).toArray(new String[playerHand.get(player).size()]);
    }

    @Override
    public int getNumMelds() {
        return melds.size();
    }

    @Override
    public String[] getMeld(int i) {
        try {
            return melds.get(i).toArray(new String[melds.get(i).size()]);
        } catch (IndexOutOfBoundsException e) {
            throw new RummyException("invalid index", RummyException.NOT_VALID_INDEX_OF_MELD);
        }
    }

    @Override
    public void rearrange(String card) {
        if (step == Steps.WAITING) {
            Stack<String> tempStack = new Stack<>();
            String currentString = "";
            while (!currentString.equals(card) && deck.size() != 0) {
                currentString = deck.pop();
                tempStack.push(currentString);
            }
            if (tempStack.size() != 0) {
                tempStack.pop();
            }

            while (tempStack.size() != 0) {
                deck.push(tempStack.pop());
            }
            deck.push(currentString);
        } else {
            throw new RummyException("Not in waiting step", RummyException.EXPECTED_WAITING_STEP);
        }
    }

    @Override
    public void shuffle(Long l) {
        if (step != Steps.WAITING) {
            throw new RummyException("invalid step", RummyException.EXPECTED_WAITING_STEP);
        }
        Random random = new Random(l);
        Set<String> tempSet = new HashSet<>();
        while (deck.size() != 0) {
            tempSet.add(deck.pop());
        }
        for (String toAdd : tempSet) {
            deck.push(toAdd);
        }
    }

    @Override
    public Steps getCurrentStep() {
        return step;
    }

    @Override
    public int isFinished() {
        if (step == Steps.FINISHED) {
            return currentPlayer;
        }
        return -1;
    }

    @Override
    public void initialDeal() {
        if (step != Steps.WAITING) {
            throw new RummyException("Invalid step", RummyException.EXPECTED_WAITING_STEP);
        }
        if (players.length == 2) {
            for (int i = 0; i < 10; i++) {
                playerHand.get(0).add(deck.pop());
                playerHand.get(1).add(deck.pop());
            }
        } else {
            int cardNum = (players.length <= 4) ? 7 : 6;
            for (int i = 0; i < cardNum; i++) {
                for (int j = 0; j < players.length; j++) {
                    playerHand.get(j).add(deck.pop());
                }
            }
        }
        discardedCard = deck.pop();
        discardPile.push(discardedCard);
        step = Steps.DRAW;
    }

    @Override
    public void drawFromDiscard() {
        if (step != Steps.DRAW) {
            throw new RummyException("Not in discard step", RummyException.EXPECTED_DRAW_STEP);
        }
        if (discardPile.size() == 0) {
            throw new RummyException("Discard Pile is empty", RummyException.NOT_VALID_DISCARD);
        }
        discardedCard = discardPile.pop();
        playerHand.get(currentPlayer).add(discardedCard);
        step = Steps.MELD;
    }

    @Override
    public void drawFromDeck() {
        if (step != Steps.DRAW) {
            throw new RummyException("Not in draw step", RummyException.EXPECTED_DRAW_STEP);
        }
        if (deck.size() == 0) {
            while (discardPile.size() != 1) {
                deck.push(discardPile.pop());
            }
            playerHand.get(currentPlayer).add(deck.pop());
        } else {
            playerHand.get(currentPlayer).add(deck.pop());
        }
        step = Steps.MELD;
    }

    @Override
    public void meld(String... cards) {
        if (step != Steps.MELD && step != Steps.RUMMY) {
            throw new RummyException("Invalid step", RummyException.EXPECTED_MELD_STEP_OR_RUMMY_STEP);
        } else if (cards.length < 3 || cards.length > 13) {
            throw new RummyException("Invalid number of cards");
        }
        for (String card : cards) {
            if (!playerHand.get(currentPlayer).contains(card)) {
                throw new RummyException("not in hand", RummyException.EXPECTED_CARDS);
            }
        }
        final String[] suits = new String[]{"C", "D", "H", "S", "M"};
        final String[] ranks = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        boolean sameRank = false; // at least 3
        //below check for same rank
        for (int i = 0; i < ranks.length; i++) {
            boolean isSameRank = true;
            for (int j = 0; j < cards.length; j++) {
                if (cards[j].charAt(0) != ranks[i].charAt(0)) {
                    isSameRank = false;
                }
            }
            if (isSameRank) {
                sameRank = isSameRank;
                break;//valid
            }
        }
        if (sameRank) {
            LinkedList<String> toAdd = new LinkedList<>();
            for (String str : cards) {
                toAdd.add(str);
            }
            melds.add(toAdd);

            notRummy[currentPlayer] = true;
            for (String card : cards) {
                playerHand.get(currentPlayer).remove(card);
            }
            if (step == Steps.RUMMY && playerHand.get(currentPlayer).size() <= 1) {
                step = Steps.FINISHED;
            }
            if (playerHand.get(currentPlayer).size() == 0) {
                step = Steps.FINISHED;
            }
            return;
        }
        //start checking for increasing sequence of same suit cards
        boolean sameSuitCards = true;
        for (int i = 0; i < suits.length; i++) {
            sameSuitCards = true;
            for (int j = 0; j < cards.length; j++) {
                if (suits[i].charAt(0) != cards[j].charAt(cards[j].length() - 1)) {
                    sameSuitCards = false;
                    break;
                }
            }
            if (sameSuitCards) {
                break;
            }
        }
        //below check if it is valid sequence
        boolean[] tempRanks = new boolean[13];
        for (int i = 0; i < cards.length; i++) {
            switch (cards[i].charAt(0)) {
                case 'J':
                    tempRanks[9] = true;
                    break;
                case 'Q':
                    tempRanks[10] = true;
                    break;
                case 'K':
                    tempRanks[11] = true;
                    break;
                case 'A':
                    tempRanks[12] = true;
                    break;
                default:
                    tempRanks[Character.getNumericValue(cards[i].charAt(0)) - 2] = true;
                    break;
            }
        }
        boolean booleanLeft = false;
        boolean booleanRight = false;
        boolean isValidSequence = true;
        for (int i = 0; i < 13; i++) {
            int previousI = i - 1;
            int nextI = i + 1;
            if (i == 0) {
                previousI = 12;
            }
            if (i == 12) {
                nextI = 0;
            }
            if (tempRanks[i] && !tempRanks[previousI]) {
                if (booleanLeft) {
                    isValidSequence = false;
                }
                booleanLeft = true;
            }
            if (tempRanks[i] && !tempRanks[nextI]) {
                if (booleanRight) {
                    isValidSequence = false;
                }
                booleanRight = true;
            }
        }
        if (isValidSequence) {
            LinkedList<String> toAdd = new LinkedList<>();
            toAdd.addAll(Arrays.asList(cards));
            melds.add(toAdd);
            notRummy[currentPlayer] = true;
            for (String card : cards) {
                playerHand.get(currentPlayer).remove(card);
            }
            if (step == Steps.RUMMY && playerHand.get(currentPlayer).size() <= 1) {
                step = Steps.FINISHED;
            }
            if (playerHand.get(currentPlayer).size() == 0) {
                step = Steps.FINISHED;
            }
        } else {
            throw new RummyException("not a meld", RummyException.NOT_VALID_MELD);
        }

    }

    @Override
    public void addToMeld(int meldIndex, String... cards) {
        if (step != Steps.MELD && step != Steps.RUMMY) {
            throw new RummyException("invalid step", RummyException.EXPECTED_MELD_STEP_OR_RUMMY_STEP);
        }
        String[] strArray = melds.get(meldIndex).toArray(new String[melds.get(meldIndex).size()]);
        String[] toSend = new String[strArray.length + cards.length];
        System.arraycopy(strArray, 0, toSend, 0, strArray.length);
        System.arraycopy(cards, 0, toSend, strArray.length, cards.length);
        for (String str : cards) {
            if (!playerHand.get(currentPlayer).contains(str)) {
                throw new RummyException("card not in hand", RummyException.EXPECTED_CARDS);
            }
        }
        final String[] suits = new String[]{"C", "D", "H", "S", "M"};
        final String[] ranks = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        boolean sameRank = false; // at least 3
        //below check for same rank
        for (int i = 0; i < ranks.length; i++) {
            boolean isSameRank = true;
            for (int j = 0; j < toSend.length; j++) {
                if (toSend[j].charAt(0) != ranks[i].charAt(0)) {
                    isSameRank = false;
                }
            }
            if (isSameRank) {
                sameRank = isSameRank;
                break;//valid
            }
        }
        if (sameRank) {
            LinkedList<String> toAdd = new LinkedList<>();
            for (String str : toSend) {
                toAdd.add(str);
            }
            melds.set(meldIndex, toAdd);
            notRummy[currentPlayer] = true;
            for (String card : cards) {
                playerHand.get(currentPlayer).remove(card);
            }
            if (step == Steps.RUMMY && playerHand.get(currentPlayer).size() <= 1) {
                step = Steps.FINISHED;
            }
            if (playerHand.get(currentPlayer).size() == 0) {
                step = Steps.FINISHED;
            }
            return;
        }
        //start checking for increasing sequence of same suit cards
        boolean sameSuitCards = true;
        for (int i = 0; i < suits.length; i++) {
            sameSuitCards = true;
            for (int j = 0; j < toSend.length; j++) {
                if (suits[i].charAt(0) != cards[j].charAt(toSend[j].length() - 1)) {
                    sameSuitCards = false;
                    break;
                }
            }
            if (sameSuitCards) {
                break;
            }
        }
        //below check if it is valid sequence
        boolean[] tempRanks = new boolean[13];
        for (int i = 0; i < toSend.length; i++) {
            switch (toSend[i].charAt(0)) {
                case 'J':
                    tempRanks[9] = true;
                    break;
                case 'Q':
                    tempRanks[10] = true;
                    break;
                case 'K':
                    tempRanks[11] = true;
                    break;
                case 'A':
                    tempRanks[12] = true;
                    break;
                case '1':
                    tempRanks[8] = true;
                    break;
                default:
                    tempRanks[Character.getNumericValue(toSend[i].charAt(0)) - 2] = true;
                    break;
            }
        }
        boolean booleanLeft = false;
        boolean booleanRight = false;
        boolean isValidSequence = true;
        for (int i = 0; i < 13; i++) {
            int previousI = i - 1;
            int nextI = i + 1;
            if (i == 0) {
                previousI = 12;
            }
            if (i == 12) {
                nextI = 0;
            }
            if (tempRanks[i] && !tempRanks[previousI]) {
                if (booleanLeft) {
                    isValidSequence = false;
                }
                booleanLeft = true;
            }
            if (tempRanks[i] && !tempRanks[nextI]) {
                if (booleanRight) {
                    isValidSequence = false;
                }
                booleanRight = true;
            }
            if (isValidSequence) {
                LinkedList<String> toAdd = new LinkedList<>();
                toAdd.addAll(Arrays.asList(toSend));
                melds.set(meldIndex, toAdd);
                notRummy[currentPlayer] = true;
                for (String card : cards) {
                    playerHand.get(currentPlayer).remove(card);
                }
                if (step == Steps.RUMMY && playerHand.get(currentPlayer).size() <= 1) {
                    step = Steps.FINISHED;
                }
                if (playerHand.get(currentPlayer).size() == 0) {
                    step = Steps.FINISHED;
                }
            } else {
                throw new RummyException("not a meld", RummyException.NOT_VALID_MELD);
            }
        }
    }

    @Override
    public void declareRummy() {
        if (step != Steps.MELD) {
            throw new RummyException("not in meld step", RummyException.EXPECTED_MELD_STEP);
        }
        if (notRummy[currentPlayer]) {
            throw new RummyException("not rummy", RummyException.RUMMY_NOT_DEMONSTRATED);
        }
        step = Steps.RUMMY;
    }

    @Override
    public void finishMeld() {
        if (step != Steps.MELD && step != Steps.RUMMY) {
            throw new RummyException("Invalid Step", RummyException.EXPECTED_MELD_STEP_OR_RUMMY_STEP);
        }
        if (step == Steps.RUMMY && playerHand.get(currentPlayer).size() > 1) {
            step = Steps.DISCARD;
            throw new RummyException("not a rummy", RummyException.RUMMY_NOT_DEMONSTRATED);
        }
        if (playerHand.get(currentPlayer).size() == 0) {
            step = Steps.FINISHED;
        } else {
            step = Steps.DISCARD;
        }
    }

    @Override
    public void discard(String card) {
        if (step != Steps.DISCARD) {
            throw new RummyException("Invalid Step", RummyException.EXPECTED_DISCARD_STEP);
        }
        if (discardedCard.equals(card)) {
            throw new RummyException("drawn and discard cards cannot be same", RummyException.NOT_VALID_DISCARD);
        }
        if (!playerHand.get(currentPlayer).contains(card)) {
            throw new RummyException("empty hand", RummyException.EXPECTED_CARDS);
        }
        discardPile.push(card);
        playerHand.get(currentPlayer).remove(card);
        notRummy[currentPlayer] = true;
        discardedCard = "";
        if (playerHand.get(currentPlayer).size() == 0) {
            step = Steps.FINISHED;
            return;
        }
        if (currentPlayer == players.length - 1) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
        step = Steps.DRAW;
    }


}

package day7;

public class HandPower {
    public HandPower(Double cardSum, Integer bet, HandPower.handRankingsEnum handRankingsEnum) {
        this.cardSum = cardSum;
        this.bet = bet;
        this.handRankingsEnum = handRankingsEnum;
    }

    public Double getCardSum() {
        return cardSum;
    }

    public void setCardSum(Double cardSum) {
        this.cardSum = cardSum;
    }

    public HandPower.handRankingsEnum getHandRankingsEnum() {
        return handRankingsEnum;
    }

    public void setHandRankingsEnum(HandPower.handRankingsEnum handRankingsEnum) {
        this.handRankingsEnum = handRankingsEnum;
    }

    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    enum handRankingsEnum {
        HIGHCARD,
        ONEPAIR,
        TWOPAIR,
        THREEOFAKIND,
        FULLHOUSE,
        FOUROFAKIND,
        FIVEOFAKIND
    }
    private Double cardSum;
    private Integer bet;
    private handRankingsEnum handRankingsEnum;
}

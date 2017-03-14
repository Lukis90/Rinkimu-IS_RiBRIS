package lt.javainiai.utils;

import lt.javainiai.model.PartyEntity;

public class WinnerPartyMultiMandate {

    private PartyEntity party;
    private Long votes;
    private Double percentOfAllBallots;
    private Long numOfMandatesWon;

    public WinnerPartyMultiMandate(PartyEntity party, Long votes, Double percentOfAllBallots, Long numOfMandatesWon) {
        this.party = party;
        this.votes = votes;
        this.percentOfAllBallots = percentOfAllBallots;
        this.numOfMandatesWon = numOfMandatesWon;
    }

    public PartyEntity getParty() {
        return party;
    }

    public void setParty(PartyEntity party) {
        this.party = party;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public Double getPercentOfAllBallots() {
        return percentOfAllBallots;
    }

    public void setPercentOfAllBallots(Double percentOfAllBallots) {
        this.percentOfAllBallots = percentOfAllBallots;
    }

    public Long getNumOfMandatesWon() {
        return numOfMandatesWon;
    }

    public void setNumOfMandatesWon(Long numOfMandatesWon) {
        this.numOfMandatesWon = numOfMandatesWon;
    }

}

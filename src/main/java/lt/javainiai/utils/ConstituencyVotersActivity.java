package lt.javainiai.utils;

import lt.javainiai.model.ConstituencyEntity;

public class ConstituencyVotersActivity {

    private ConstituencyEntity constituency;
    private Long givenBallots;
    private Double percentOfAllVoters;

    public ConstituencyVotersActivity(ConstituencyEntity constituency, Long givenBallots, Double percentOfAllVoters) {
        this.constituency = constituency;
        this.givenBallots = givenBallots;
        this.percentOfAllVoters = percentOfAllVoters;
    }

    public ConstituencyEntity getConstituency() {
        return constituency;
    }

    public void setConstituency(ConstituencyEntity constituency) {
        this.constituency = constituency;
    }

    public Long getGivenBallots() {
        return givenBallots;
    }

    public void setGivenBallots(Long givenBallots) {
        this.givenBallots = givenBallots;
    }

    public Double getPercentOfAllVoters() {
        return percentOfAllVoters;
    }

    public void setPercentOfAllVoters(Double percentOfAllVoters) {
        this.percentOfAllVoters = percentOfAllVoters;
    }

}

package lt.javainiai.utils;

import lt.javainiai.model.PollingDistrictEntity;

public class DistrictVotersActivity {

    private PollingDistrictEntity district;
    private Long givenBallots;
    private Double percentOfAllVoters;

    public DistrictVotersActivity(PollingDistrictEntity district, Long givenBallots, Double percentOfAllVoters) {
        this.district = district;
        this.givenBallots = givenBallots;
        this.percentOfAllVoters = percentOfAllVoters;
    }

    public PollingDistrictEntity getDistrict() {
        return district;
    }

    public void setDistrict(PollingDistrictEntity district) {
        this.district = district;
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

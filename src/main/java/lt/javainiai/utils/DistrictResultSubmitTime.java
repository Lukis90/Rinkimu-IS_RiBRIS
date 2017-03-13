package lt.javainiai.utils;

import lt.javainiai.model.PollingDistrictEntity;

public class DistrictResultSubmitTime {

    private PollingDistrictEntity district;
    private String resultsDateString;

    public DistrictResultSubmitTime(PollingDistrictEntity district, String resultsDateString) {
        this.district = district;
        this.resultsDateString = resultsDateString;
    }

    public PollingDistrictEntity getDistrict() {
        return district;
    }

    public void setDistrict(PollingDistrictEntity district) {
        this.district = district;
    }

    public String getResultsDateString() {
        return resultsDateString;
    }

    public void setResultsDateString(String resultsDateString) {
        this.resultsDateString = resultsDateString;
    }

}

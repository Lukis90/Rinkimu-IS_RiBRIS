package lt.javainiai.utils;

import lt.javainiai.model.ConstituencyEntity;

public class ConstituencyProgress {

    private ConstituencyEntity constituency;
    private Long totalNumOfDistricts;
    private Long districtsWithResults;

    public ConstituencyProgress(ConstituencyEntity constituency, Long totalNumOfDistricts, Long districtsWithResults) {
        this.constituency = constituency;
        this.totalNumOfDistricts = totalNumOfDistricts;
        this.districtsWithResults = districtsWithResults;
    }

    public ConstituencyEntity getConstituency() {
        return constituency;
    }

    public void setConstituency(ConstituencyEntity constituency) {
        this.constituency = constituency;
    }

    public Long getTotalNumOfDistricts() {
        return totalNumOfDistricts;
    }

    public void setTotalNumOfDistricts(Long totalNumOfDistricts) {
        this.totalNumOfDistricts = totalNumOfDistricts;
    }

    public Long getDistrictsWithResults() {
        return districtsWithResults;
    }

    public void setDistrictsWithResults(Long districtsWithResults) {
        this.districtsWithResults = districtsWithResults;
    }

}

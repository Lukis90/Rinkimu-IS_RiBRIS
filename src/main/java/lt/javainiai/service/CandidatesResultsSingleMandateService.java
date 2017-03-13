package lt.javainiai.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.javainiai.model.CandidateEntity;
import lt.javainiai.model.CandidatesResultsSingleMandateEntity;
import lt.javainiai.model.ConstituencyEntity;
import lt.javainiai.model.PollingDistrictEntity;
import lt.javainiai.repository.CandidatesResultsSingleMandateRepository;
import lt.javainiai.utils.ConstituencyProgress;
import lt.javainiai.utils.DistrictResultSubmitTime;
import lt.javainiai.utils.SingleMandateCandidateResults;
import lt.javainiai.utils.UtilityMethods;

@Service
public class CandidatesResultsSingleMandateService {

    @Autowired
    private CandidatesResultsSingleMandateRepository candidatesResultsRepository;
    @Autowired
    private PollingDistrictService pollingDistrictService;
    @Autowired
    private ConstituencyService constituencyService;

    public CandidatesResultsSingleMandateEntity saveOrUpdate(CandidatesResultsSingleMandateEntity candidatesResults) {
        return candidatesResultsRepository.saveOrUpdate(candidatesResults);
    }

    public List<CandidatesResultsSingleMandateEntity> findAll() {
        return candidatesResultsRepository.findAll();
    }

    public CandidatesResultsSingleMandateEntity findById(Long id) {
        return candidatesResultsRepository.findById(id);
    }

    public void deleteById(Long id) {
        this.candidatesResultsRepository.deleteById(id);
    }

    public List<SingleMandateCandidateResults> getSingleMandateResultsInDistrict(Long districtId) {
        PollingDistrictEntity district = pollingDistrictService.findById(districtId);
        List<SingleMandateCandidateResults> districtResultsList = new ArrayList<>();
        List<CandidateEntity> candidates = district.getConstituency().getCandidates();
        List<CandidatesResultsSingleMandateEntity> results = findAll();
        Long validVotes = 0L;
        Long allBallots = pollingDistrictService.getVotersActivityInUnitsInDistrict(districtId);

        for (CandidatesResultsSingleMandateEntity result : results) {
            if (result.getDistrict().equals(district)) {
                validVotes += result.getNumberOfVotes();
            }
        }

        for (CandidateEntity candidate : candidates) {
            List<CandidatesResultsSingleMandateEntity> candidateResultsList = candidate
                    .getCandidatesResultsSingleMandate();
            SingleMandateCandidateResults candidateResult;
            Long candidateVotes = 0L;
            Double percentOfValidBallots = null;
            Double percentOfAllBallots = null;

            for (CandidatesResultsSingleMandateEntity result : candidateResultsList) {
                if (result.getDistrict().equals(district)) {
                    candidateVotes = result.getNumberOfVotes();
                    break;
                }
            }

            percentOfValidBallots = (candidateVotes.doubleValue() / validVotes.doubleValue()) * 100.0d;
            percentOfValidBallots = UtilityMethods.round(percentOfValidBallots, 2);

            percentOfAllBallots = (candidateVotes.doubleValue() / allBallots.doubleValue()) * 100.0d;
            percentOfAllBallots = UtilityMethods.round(percentOfAllBallots, 2);

            candidateResult = new SingleMandateCandidateResults(candidate, candidateVotes, percentOfValidBallots,
                    percentOfAllBallots);
            districtResultsList.add(candidateResult);
        }
        return districtResultsList;
    }

    public List<SingleMandateCandidateResults> getSingleMandateResultsInConstituency(Long constituencyId) {
        ConstituencyEntity constituency = constituencyService.findById(constituencyId);
        List<SingleMandateCandidateResults> constituencyResultsList = new ArrayList<>();
        List<PollingDistrictEntity> districts = constituency.getPollingDistricts();
        List<CandidateEntity> candidates = constituencyService.findById(constituencyId).getCandidates();
        List<CandidatesResultsSingleMandateEntity> results = findAll();
        Long validVotes = 0L;
        Long spoiledBallots = 0L;
        Long allBallots = 0L;

        for (CandidatesResultsSingleMandateEntity result : results) {
            if (result.getDistrict().getConstituency().equals(constituency)) {
                validVotes += result.getNumberOfVotes();
            }
        }

        for (PollingDistrictEntity district : districts) {
            spoiledBallots += district.getSpoiledSingleMandateBallots();
        }
        allBallots = validVotes + spoiledBallots;

        for (CandidateEntity candidate : candidates) {
            List<CandidatesResultsSingleMandateEntity> candidateResultsList = candidate
                    .getCandidatesResultsSingleMandate();
            SingleMandateCandidateResults candidateResult;
            Long candidateVotes = 0L;
            Double percentOfValidBallots = null;
            Double percentOfAllBallots = null;

            for (CandidatesResultsSingleMandateEntity result : candidateResultsList) {
                if (result.getDistrict().getConstituency().equals(constituency)) {
                    candidateVotes += result.getNumberOfVotes();
                }
            }

            percentOfValidBallots = (candidateVotes.doubleValue() / validVotes.doubleValue()) * 100.0d;
            percentOfValidBallots = UtilityMethods.round(percentOfValidBallots, 2);

            percentOfAllBallots = (candidateVotes.doubleValue() / allBallots.doubleValue()) * 100.0d;
            percentOfAllBallots = UtilityMethods.round(percentOfAllBallots, 2);

            candidateResult = new SingleMandateCandidateResults(candidate, candidateVotes, percentOfValidBallots,
                    percentOfAllBallots);
            constituencyResultsList.add(candidateResult);
        }
        return constituencyResultsList;
    }

    public List<ConstituencyProgress> getConstituenciesProgressList() {
        List<ConstituencyProgress> constituenciesProgressList = new ArrayList<>();
        List<ConstituencyEntity> constituencies = constituencyService.findAll();

        for (ConstituencyEntity constituency : constituencies) {
            List<PollingDistrictEntity> districts = constituency.getPollingDistricts();
            ConstituencyProgress progress;
            Long totalNumOfDistricts = new Long(districts.size());
            Long districtsWithResults = 0L;

            for (PollingDistrictEntity district : districts) {
                long totalOfCandidates = district.getConstituency().getCandidates().size();
                long numberOfCandidatesWithSubmittedResults = district.getSingleMandateResults().size();

                if (totalOfCandidates == numberOfCandidatesWithSubmittedResults) {
                    districtsWithResults++;
                }
            }
            progress = new ConstituencyProgress(constituency, totalNumOfDistricts, districtsWithResults);
            constituenciesProgressList.add(progress);
        }
        return constituenciesProgressList;
    }

    public List<DistrictResultSubmitTime> getDistrictsResultsSubmissionTime(Long constituencyId) {
        List<DistrictResultSubmitTime> districtResultsSubmissionTimeList = new ArrayList<>();
        List<PollingDistrictEntity> districts = constituencyService.findById(constituencyId).getPollingDistricts();

        for (PollingDistrictEntity district : districts) {
            List<CandidatesResultsSingleMandateEntity> results = district.getSingleMandateResults();
            DistrictResultSubmitTime districtResultsSubmissionTime;
            Date resultsDate = null;
            String resultsDateString = "Rezultatai nepateikti";

            if (!results.isEmpty()) {
                for (CandidatesResultsSingleMandateEntity result : results) {
                    resultsDate = result.getCreated();
                    break;
                }
                try {
                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    resultsDateString = dt.format(resultsDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            districtResultsSubmissionTime = new DistrictResultSubmitTime(district, resultsDateString);
            districtResultsSubmissionTimeList.add(districtResultsSubmissionTime);
        }
        return districtResultsSubmissionTimeList;
    }

}
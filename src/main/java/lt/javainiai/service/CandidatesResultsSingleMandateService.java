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

    // Counts single-mandate results in specified polling-district.
    public List<SingleMandateCandidateResults> getSingleMandateResultsInDistrict(Long districtId) {

        List<SingleMandateCandidateResults> districtResultsList = new ArrayList<SingleMandateCandidateResults>();
        PollingDistrictEntity district = pollingDistrictService.findById(districtId);
        List<CandidateEntity> candidates = district.getConstituency().getCandidates();
        Long validVotes = 0L;
        Long allBallots = pollingDistrictService.getVotersActivityInUnitsInDistrict(districtId);

        // Count all valid single-member votes in district
        List<CandidatesResultsSingleMandateEntity> results = findAll();
        for (CandidatesResultsSingleMandateEntity result : results) {
            if (result.getDistrict() == district) {
                validVotes += result.getNumberOfVotes();
            }
        }

        // Fill districtResultsList with results of every candidate in district
        for (CandidateEntity candidate : candidates) {
            Long candidateVotes = 0L;
            Double percentOfValidBallots = null;
            Double percentOfAllBallots = null;

            // Get result in units in one district
            List<CandidatesResultsSingleMandateEntity> candidateResultsList = candidate
                    .getCandidatesResultsSingleMandate();
            for (CandidatesResultsSingleMandateEntity result : candidateResultsList) {
                if (result.getDistrict() == district) {
                    candidateVotes = result.getNumberOfVotes();
                    break;
                }
            }

            // Get result in percent of valid ballots in one district
            percentOfValidBallots = (candidateVotes.doubleValue() / validVotes.doubleValue()) * 100.0d;
            percentOfValidBallots = UtilityMethods.round(percentOfValidBallots, 2);

            // Get result in percent of all ballots in one district
            percentOfAllBallots = (candidateVotes.doubleValue() / allBallots.doubleValue()) * 100.0d;
            percentOfAllBallots = UtilityMethods.round(percentOfAllBallots, 2);

            SingleMandateCandidateResults candidateResults = new SingleMandateCandidateResults(candidate,
                    candidateVotes, percentOfValidBallots, percentOfAllBallots);

            districtResultsList.add(candidateResults);
        }
        return districtResultsList;
    }

    // Single-mandate results in Constituency
    public List<SingleMandateCandidateResults> getSingleMandateResultsInConstituency(Long constituencyId) {
        List<SingleMandateCandidateResults> constituencyResultsList = new ArrayList<SingleMandateCandidateResults>();
        ConstituencyEntity constituency = constituencyService.findById(constituencyId);
        List<PollingDistrictEntity> districts = constituency.getPollingDistricts();
        List<CandidateEntity> candidates = constituencyService.findById(constituencyId).getCandidates();
        Long validVotes = 0L;
        Long spoiledBallots = 0L;
        Long allBallots = 0L;

        // Count all valid single-member votes in constituency
        List<CandidatesResultsSingleMandateEntity> results = findAll();
        for (CandidatesResultsSingleMandateEntity result : results) {
            if (result.getDistrict().getConstituency() == constituency) {
                validVotes += result.getNumberOfVotes();
            }
        }

        // Count all ballots in single-member constituency
        for (PollingDistrictEntity district : districts) {
            spoiledBallots += district.getSpoiledSingleMandateBallots();
        }
        allBallots = validVotes + spoiledBallots;

        // Fill List of single-member candidates with their results
        for (CandidateEntity candidate : candidates) {
            Long candidateVotes = 0L;
            Double percentOfValidBallots = null;
            Double percentOfAllBallots = null;

            // Count candidate results in Constituency
            List<CandidatesResultsSingleMandateEntity> candidateResultsList = candidate
                    .getCandidatesResultsSingleMandate();
            for (CandidatesResultsSingleMandateEntity result : candidateResultsList) {
                if (result.getDistrict().getConstituency() == constituency) {
                    candidateVotes += result.getNumberOfVotes();
                }
            }

            // Count percent of valid ballots
            percentOfValidBallots = (candidateVotes.doubleValue() / validVotes.doubleValue()) * 100.0d;
            percentOfValidBallots = UtilityMethods.round(percentOfValidBallots, 2);

            // Count percent of all ballots
            percentOfAllBallots = (candidateVotes.doubleValue() / allBallots.doubleValue()) * 100.0d;
            percentOfAllBallots = UtilityMethods.round(percentOfAllBallots, 2);

            // Create candidate result object and add to List
            SingleMandateCandidateResults candidateResults = new SingleMandateCandidateResults(candidate,
                    candidateVotes, percentOfValidBallots, percentOfAllBallots);
            constituencyResultsList.add(candidateResults);
        }
        return constituencyResultsList;
    }

    public List<ConstituencyProgress> getConstituenciesProgressList() {
        List<ConstituencyProgress> constituenciesProgressList = new ArrayList<>();
        List<ConstituencyEntity> constituencies = constituencyService.findAll();

        for (ConstituencyEntity constituency : constituencies) {
            List<PollingDistrictEntity> districts = constituency.getPollingDistricts();
            Long totalNumOfDistricts = new Long(districts.size());
            Long districtsWithResults = 0L;

            for (PollingDistrictEntity district : districts) {

                // Check if polling district has submitted results for all
                // parties
                long totalOfCandidates = district.getConstituency().getCandidates().size();
                long numberOfCandidatesWithSubmittedResults = district.getSingleMandateResults().size();

                if (totalOfCandidates == numberOfCandidatesWithSubmittedResults) {
                    district.setSubmittedSingleResults(true);
                } else {
                    district.setSubmittedSingleResults(false);
                }
                pollingDistrictService.saveOrUpdate(district);

                if (district.getSubmittedSingleResults()) {
                    districtsWithResults++;
                }
            }
            ConstituencyProgress progress = new ConstituencyProgress(constituency, totalNumOfDistricts,
                    districtsWithResults);
            constituenciesProgressList.add(progress);
        }
        return constituenciesProgressList;
    }

    public List<DistrictResultSubmitTime> getDistrictsResultsSubmissionTime(Long constituencyId) {
        List<DistrictResultSubmitTime> districtResultsSubmissionTimeList = new ArrayList<>();
        List<PollingDistrictEntity> districts = constituencyService.findById(constituencyId).getPollingDistricts();

        for (PollingDistrictEntity district : districts) {
            Date resultsDate = null;
            String resultsDateString = "Rezultatai nepateikti";

            List<CandidatesResultsSingleMandateEntity> results = district.getSingleMandateResults();

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
            DistrictResultSubmitTime districtResultsSubmissionTime = new DistrictResultSubmitTime(district,
                    resultsDateString);
            districtResultsSubmissionTimeList.add(districtResultsSubmissionTime);
        }
        return districtResultsSubmissionTimeList;
    }

}
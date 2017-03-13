package lt.javainiai.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.javainiai.model.ConstituencyEntity;
import lt.javainiai.model.PartyEntity;
import lt.javainiai.model.PartyResultsEntity;
import lt.javainiai.model.PollingDistrictEntity;
import lt.javainiai.repository.PartyResultsRepository;
import lt.javainiai.utils.ConstituencyProgress;
import lt.javainiai.utils.DistrictResultSubmitTime;
import lt.javainiai.utils.MultiMandatePartyResults;
import lt.javainiai.utils.UtilityMethods;

@Service
public class PartyResultsService {

    @Autowired
    private PartyResultsRepository partyResultsRepository;
    @Autowired
    private PollingDistrictService pollingDistrictService;
    @Autowired
    private ConstituencyService constituencyService;
    @Autowired
    private PartyService partyService;

    public PartyResultsEntity saveOrUpdate(PartyResultsEntity partyResults) {
        return partyResultsRepository.saveOrUpdate(partyResults);
    }

    public List<PartyResultsEntity> findAll() {
        return this.partyResultsRepository.findAll();
    }

    public PartyResultsEntity findById(Long id) {
        return this.partyResultsRepository.findById(id);
    }

    public void deleteById(Long id) {
        this.partyResultsRepository.deleteById(id);
    }

    public List<MultiMandatePartyResults> getMultiMandateResultsInDistrict(Long districtId) {
        List<MultiMandatePartyResults> districtResultsList = new ArrayList<>();
        List<PartyEntity> parties = partyService.findAll();
        List<PartyResultsEntity> allPartyResults = findAll();
        PollingDistrictEntity district = pollingDistrictService.findById(districtId);
        Long validVotes = 0L;
        Long allBallots = pollingDistrictService.getVotersActivityInUnitsInDistrict(districtId);

        for (PartyResultsEntity result : allPartyResults) {
            if (result.getDistrict().equals(district)) {
                validVotes += result.getNumberOfVotes();
            }
        }

        for (PartyEntity party : parties) {
            MultiMandatePartyResults partyResult;
            Long partyVotes = 0L;
            Double percentOfValidBallots = null;
            Double percentOfAllBallots = null;

            List<PartyResultsEntity> partyResults = party.getPartyResults();
            for (PartyResultsEntity result : partyResults) {
                if (result.getDistrict().equals(district)) {
                    partyVotes = result.getNumberOfVotes();
                    break;
                }
            }

            percentOfValidBallots = (partyVotes.doubleValue() / validVotes.doubleValue()) * 100.0d;
            percentOfValidBallots = UtilityMethods.round(percentOfValidBallots, 2);

            percentOfAllBallots = (partyVotes.doubleValue() / allBallots.doubleValue()) * 100.0d;
            percentOfAllBallots = UtilityMethods.round(percentOfAllBallots, 2);

            partyResult = new MultiMandatePartyResults(party, partyVotes, percentOfValidBallots, percentOfAllBallots);
            districtResultsList.add(partyResult);
        }
        return districtResultsList;
    }

    public List<MultiMandatePartyResults> getMultiMandateResultsInConstituency(Long constituencyId) {
        ConstituencyEntity constituency = constituencyService.findById(constituencyId);
        List<MultiMandatePartyResults> constituencyResultsList = new ArrayList<>();
        List<PartyResultsEntity> results = findAll();
        List<PollingDistrictEntity> districts = constituency.getPollingDistricts();
        List<PartyEntity> parties = partyService.findAll();
        Long validVotes = 0L;
        Long spoiledBallots = 0L;
        Long allBallots = 0L;

        for (PartyResultsEntity result : results) {
            if (result.getDistrict().getConstituency().equals(constituency)) {
                validVotes += result.getNumberOfVotes();
            }
        }

        for (PollingDistrictEntity district : districts) {
            spoiledBallots += district.getSpoiledMultiMandateBallots();
        }
        allBallots = validVotes + spoiledBallots;

        for (PartyEntity party : parties) {
            List<PartyResultsEntity> partyResultsList = party.getPartyResults();
            MultiMandatePartyResults partyResult;
            Long partyVotes = 0L;
            Double percentOfValidBallots;
            Double percentOfAllBallots;

            for (PartyResultsEntity result : partyResultsList) {
                if (result.getDistrict().getConstituency().equals(constituency)) {
                    partyVotes += result.getNumberOfVotes();
                }
            }

            percentOfValidBallots = (partyVotes.doubleValue() / validVotes.doubleValue()) * 100.0d;
            percentOfValidBallots = UtilityMethods.round(percentOfValidBallots, 2);

            percentOfAllBallots = (partyVotes.doubleValue() / allBallots.doubleValue()) * 100.0d;
            percentOfAllBallots = UtilityMethods.round(percentOfAllBallots, 2);

            partyResult = new MultiMandatePartyResults(party, partyVotes, percentOfValidBallots, percentOfAllBallots);
            constituencyResultsList.add(partyResult);
        }
        return constituencyResultsList;
    }

    public List<MultiMandatePartyResults> getMultiMandateTotalResults() {
        List<MultiMandatePartyResults> totalResultsList = new ArrayList<>();
        List<PartyResultsEntity> results = findAll();
        List<PollingDistrictEntity> districts = pollingDistrictService.findAll();
        List<PartyEntity> parties = partyService.findAll();
        Long validVotes = 0L;
        Long spoiledBallots = 0L;
        Long allBallots = 0L;

        for (PartyResultsEntity result : results) {
            validVotes += result.getNumberOfVotes();
        }

        for (PollingDistrictEntity district : districts) {
            spoiledBallots += district.getSpoiledMultiMandateBallots();
        }
        allBallots = validVotes + spoiledBallots;

        for (PartyEntity party : parties) {
            List<PartyResultsEntity> partyResultsList = party.getPartyResults();
            MultiMandatePartyResults partyResult;
            Long partyVotes = 0L;
            Double percentOfValidBallots;
            Double percentOfAllBallots;

            for (PartyResultsEntity result : partyResultsList) {
                partyVotes += result.getNumberOfVotes();
            }

            percentOfValidBallots = (partyVotes.doubleValue() / validVotes.doubleValue()) * 100.0d;
            percentOfValidBallots = UtilityMethods.round(percentOfValidBallots, 2);

            percentOfAllBallots = (partyVotes.doubleValue() / allBallots.doubleValue()) * 100.0d;
            percentOfAllBallots = UtilityMethods.round(percentOfAllBallots, 2);

            partyResult = new MultiMandatePartyResults(party, partyVotes, percentOfValidBallots, percentOfAllBallots);
            totalResultsList.add(partyResult);
        }
        return totalResultsList;
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
                long totalOfParties = partyService.findAll().size();
                long numberOfPartiesWithSubmittedResults = district.getPartyResults().size();

                if (totalOfParties == numberOfPartiesWithSubmittedResults) {
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
            List<PartyResultsEntity> results = district.getPartyResults();
            DistrictResultSubmitTime districtResultsSubmissionTime;
            Date resultsDate = null;
            String resultsDateString = "Rezultatai nepateikti";

            if (!results.isEmpty()) {
                for (PartyResultsEntity result : results) {
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

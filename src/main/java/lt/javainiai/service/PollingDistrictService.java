package lt.javainiai.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.javainiai.model.CandidatesResultsSingleMandateEntity;
import lt.javainiai.model.PartyResultsEntity;
import lt.javainiai.model.PollingDistrictEntity;
import lt.javainiai.repository.PollingDistrictRepository;
import lt.javainiai.utils.DistrictVotersActivity;
import lt.javainiai.utils.SpoiledResults;
import lt.javainiai.utils.UtilityMethods;

@Service
public class PollingDistrictService {

    @Autowired
    private PollingDistrictRepository pollingDistrictRepository;
    @Autowired
    private ConstituencyService constituencyService;
    @Autowired
    private PartyService partyService;

    public PollingDistrictEntity saveOrUpdate(PollingDistrictEntity pollingDistrict) {
        return pollingDistrictRepository.saveOrUpdate(pollingDistrict);
    }

    public List<PollingDistrictEntity> findAll() {
        return pollingDistrictRepository.findAll();
    }

    public PollingDistrictEntity findById(Long id) {
        return pollingDistrictRepository.findById(id);
    }

    public void deleteById(Long id) {
        pollingDistrictRepository.deleteById(id);
    }

    // Voters activity (3 following methods)
    public Long getVotersActivityInUnitsInDistrict(Long districtId) {
        PollingDistrictEntity district = findById(districtId);
        List<CandidatesResultsSingleMandateEntity> districtSingleMemberResultsList = district.getSingleMandateResults();
        List<PartyResultsEntity> districtMultiMemberResultsList;
        Long sumOfVotes = 0L;
        Long spoiledBallots;

        if (!districtSingleMemberResultsList.isEmpty()) {
            for (CandidatesResultsSingleMandateEntity candidateResult : districtSingleMemberResultsList) {
                sumOfVotes += candidateResult.getNumberOfVotes();
            }
            spoiledBallots = district.getSpoiledSingleMandateBallots();
        } else {
            districtMultiMemberResultsList = district.getPartyResults();
            for (PartyResultsEntity partyResult : districtMultiMemberResultsList) {
                sumOfVotes += partyResult.getNumberOfVotes();
            }
            spoiledBallots = district.getSpoiledMultiMandateBallots();
        }
        return sumOfVotes + spoiledBallots;
    }

    public Double getVotersActivityInPercentInDistrict(Long districtId) {
        Long givenBallots = getVotersActivityInUnitsInDistrict(districtId);
        Long totalOfVoters = findById(districtId).getNumOfVoters();
        Double percent = (givenBallots.doubleValue() / totalOfVoters.doubleValue()) * 100.0d;
        percent = UtilityMethods.round(percent, 2);
        return percent;
    }

    public List<DistrictVotersActivity> getVotersActivityInAllDistrictsOfConstituency(Long constituencyId) {
        List<DistrictVotersActivity> activityInDistrictsList = new ArrayList<DistrictVotersActivity>();
        List<PollingDistrictEntity> districts = constituencyService.findById(constituencyId).getPollingDistricts();

        for (PollingDistrictEntity district : districts) {
            Long districtId = district.getId();
            Long givenBallots = getVotersActivityInUnitsInDistrict(districtId);
            Double percentOfAllVoters = getVotersActivityInPercentInDistrict(districtId);
            DistrictVotersActivity districtActivity = new DistrictVotersActivity(district, givenBallots,
                    percentOfAllVoters);
            activityInDistrictsList.add(districtActivity);
        }
        return activityInDistrictsList;
    }

    @Transactional
    public PollingDistrictEntity postSpoiledBallots(Long districtId, SpoiledResults results) {
        Long single;
        Long multi;

        if (results.getSpoiledSingle() == null) {
            single = 0L;
        } else {
            single = results.getSpoiledSingle();
        }

        if (results.getSpoiledMulti() == null) {
            multi = 0L;
        } else {
            multi = results.getSpoiledMulti();
        }
        return pollingDistrictRepository.postSpoiledBallots(districtId, single, multi);
    }

    public List<PollingDistrictEntity> getDistrictsNotSubmittedSingleResults() {
        List<PollingDistrictEntity> districtsNotSubmittedresults = new ArrayList<>();
        List<PollingDistrictEntity> allDistricts = findAll();

        for (PollingDistrictEntity district : allDistricts) {
            long totalOfCandidates = district.getConstituency().getCandidates().size();
            long numberOfCandidatesWithSubmittedResults = district.getSingleMandateResults().size();

            if (numberOfCandidatesWithSubmittedResults < totalOfCandidates) {
                districtsNotSubmittedresults.add(district);
            }
        }
        return districtsNotSubmittedresults;
    }

    public List<PollingDistrictEntity> getDistrictsNotSubmittedMultiResults() {
        List<PollingDistrictEntity> districtsNotSubmittedresults = new ArrayList<>();
        List<PollingDistrictEntity> allDistricts = findAll();

        for (PollingDistrictEntity district : allDistricts) {
            long totalOfParties = partyService.findAll().size();
            long numberOfPartiesWithSubmittedResults = district.getPartyResults().size();

            if (numberOfPartiesWithSubmittedResults < totalOfParties) {
                districtsNotSubmittedresults.add(district);
            }
        }
        return districtsNotSubmittedresults;
    }

}
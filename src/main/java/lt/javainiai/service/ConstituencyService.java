package lt.javainiai.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import lt.javainiai.model.CandidateEntity;
import lt.javainiai.model.CandidatesResultsSingleMandateEntity;
import lt.javainiai.model.ConstituencyEntity;
import lt.javainiai.model.PollingDistrictEntity;
import lt.javainiai.repository.ConstituencyRepository;
import lt.javainiai.utils.ConstituencyVotersActivity;
import lt.javainiai.utils.UtilityMethods;

@Service
public class ConstituencyService {

	@Autowired
	private ConstituencyRepository constituencyRepository;
	@Autowired
	private CandidateService candidateService;

	public ConstituencyEntity saveOrUpdate(Long id, String constituencyName, MultipartFile csvFile) {

		ConstituencyEntity constituency = new ConstituencyEntity();
		constituency.setId(id);
		constituency.setName(constituencyName);
		// save party to Database and get response from repository;
		ConstituencyEntity constituencyResponse = constituencyRepository.saveOrUpdate(constituency);

		List<CandidateEntity> candidateList = new ArrayList<>();

		File file = null;
		InputStreamReader inputStreamReader = null;
		try {
			file = UtilityMethods.multipartToFile(csvFile);
			InputStream fileInputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// to store one line from file
		String data = "";
		// array of values from one row of CSV file
		String[] values = {};

		try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
			while ((data = bufferedReader.readLine()) != null) {
				values = data.split(",", -1);

				CandidateEntity candidate = new CandidateEntity();
				candidate.setPersonsId(Long.valueOf(values[0]));
				candidate.setName(values[1]);
				candidate.setSurname(values[2]);
				candidate.setBirthDate(formatDate(values[3]));
				candidate.setConstituency(constituencyResponse);
				candidate.setMultiMandate(Boolean.valueOf(values[4]));
				candidate.setBiography(values[5]);
				candidate.setListPossition(Long.valueOf(values[6]));

				candidateList.add(candidate);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (CandidateEntity candidate : candidateList) {
			candidateService.saveOrUpdate(candidate);
		}
		FileSystemUtils.deleteRecursively(file);
		return constituencyResponse;
	}

	// Save or update party (no CSV candidate list)
	public ConstituencyEntity saveOrUpdate(Long id, String constituencyName) {

		ConstituencyEntity constituency = new ConstituencyEntity();
		constituency.setId(id);
		constituency.setName(constituencyName);
		// save party to Database and get response from repository;
		return constituencyRepository.saveOrUpdate(constituency);
	}

	public List<ConstituencyEntity> findAll() {
		return constituencyRepository.findAll();
	}

	public ConstituencyEntity findById(Long id) {
		return constituencyRepository.findById(id);
	}

	public ConstituencyEntity findByName(String name) {
		return constituencyRepository.findByName(name);
	}

	public void deleteById(Long id) {
		constituencyRepository.deleteById(id);
	}

	// Voters activity (3 following methods)
	public Long getVotersActivityInUnitsInConstituency(Long constituencyId) {
		Long sumOfVotes = 0L;
		ConstituencyEntity constituency = findById(constituencyId);
		List<PollingDistrictEntity> districts = constituency.getPollingDistricts();

		for (PollingDistrictEntity district : districts) {
			List<CandidatesResultsSingleMandateEntity> districtSingleMemberResultsList = district
					.getSingleMandateResults();

			for (CandidatesResultsSingleMandateEntity candidateResult : districtSingleMemberResultsList) {
				sumOfVotes += candidateResult.getNumberOfVotes();
			}
			sumOfVotes += district.getSpoiledSingleMandateBallots();
		}
		return sumOfVotes;
	}

	public Double getVotersActivityInPercentInConstituency(Long constituencyId) {
		Long sumOfVotes = getVotersActivityInUnitsInConstituency(constituencyId);
		Long totalOfVoters = 0L;

		ConstituencyEntity constituency = findById(constituencyId);
		List<PollingDistrictEntity> districts = constituency.getPollingDistricts();
		for (PollingDistrictEntity district : districts) {
			totalOfVoters += district.getNumOfVoters();
		}

		Double percent = (sumOfVotes.doubleValue() / totalOfVoters.doubleValue()) * 100.0d;
		percent = UtilityMethods.round(percent, 2);

		return percent;
	}

	public List<ConstituencyVotersActivity> getVotersActivityInAllConstituencies() {
		List<ConstituencyVotersActivity> activityInConstituenciesList = new ArrayList<ConstituencyVotersActivity>();
		List<ConstituencyEntity> constituencies = findAll();

		for (ConstituencyEntity constituency : constituencies) {
			Long constituencyId = constituency.getId();
			Long givenBallots = getVotersActivityInUnitsInConstituency(constituencyId);
			Double percentOfAllVoters = getVotersActivityInPercentInConstituency(constituencyId);

			ConstituencyVotersActivity constituencyActivity = new ConstituencyVotersActivity(constituency, givenBallots,
					percentOfAllVoters);
			activityInConstituenciesList.add(constituencyActivity);
		}
		return activityInConstituenciesList;
	}

	// Converts date (String) from CSV to java.util.Date, then to java.sql.Date
	// (needed for candidate.setBirthDate method)
	private java.sql.Date formatDate(String dateString) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date birthDate = null;

		try {
			Date date = (Date) df.parse(dateString);
			birthDate = new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return birthDate;
	}

}
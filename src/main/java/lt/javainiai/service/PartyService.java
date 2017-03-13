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
import lt.javainiai.model.PartyEntity;
import lt.javainiai.repository.PartyRepository;
import lt.javainiai.utils.UtilityMethods;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private CandidateService candidateService;

    // Save or update party (with CSV candidate list)
    public PartyEntity saveOrUpdate(Long id,String partyName, Long partyNo, MultipartFile csvFile) {

        PartyEntity party = new PartyEntity();
        party.setId(id);
        party.setName(partyName);
        party.setPartyNo(partyNo);
        // save party to Database and get response from repository;
        PartyEntity partyResponse = partyRepository.saveOrUpdate(party);

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
                candidate.setParty(partyResponse);
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
        return partyResponse;
    }

    // Save or update party (no CSV candidate list)
    public PartyEntity saveOrUpdate(Long id,String partyName, Long partyNo) {

        PartyEntity party = new PartyEntity();
        party.setId(id);
        party.setName(partyName);
        party.setPartyNo(partyNo);
        // save party to Database and get response from repository;
        return partyRepository.saveOrUpdate(party);
    }

    public List<PartyEntity> findAll() {
        return this.partyRepository.findAll();
    }

    public PartyEntity findById(Long id) {
        return this.partyRepository.findById(id);
    }

    public void deleteById(Long id) {
        this.partyRepository.deleteById(id);
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

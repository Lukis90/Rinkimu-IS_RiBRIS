package lt.javainiai.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lt.javainiai.model.PartyEntity;
import lt.javainiai.repository.PartyRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RiBRIS_Application.class })
//@DirtiesContext(classMode =  ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class PartyRepositoryTest {
	
	@Autowired
	PartyRepository repository;

	@Test
	public void insertTest() {
		final String partyName = "Konservatoriai";
		
		PartyEntity party = new PartyEntity();
		party.setName(partyName);
		party.setPartyNo(Long.valueOf(1));
		
		repository.saveOrUpdate(party);
		
		PartyEntity dbParty = repository.findById(party.getId());
		assertNotNull(dbParty);
		assertEquals(partyName, dbParty.getName());
	}

}

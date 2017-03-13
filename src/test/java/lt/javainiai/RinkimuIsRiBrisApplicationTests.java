package lt.javainiai;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import lt.javainiai.controller.CandidateControllerIT;
import lt.javainiai.controller.ConstituencyControllerIT;

import lt.javainiai.controller.PartyControllerIT;
import lt.javainiai.controller.PollingDistrictControllerIT;
import lt.javainiai.controller.RepresentativeControllerIT;



@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConstituencyControllerIT.class,
        PollingDistrictControllerIT.class,
        CandidateControllerIT.class,
        PartyControllerIT.class,
        RepresentativeControllerIT.class
})

public class RinkimuIsRiBrisApplicationTests {

	@Test
	public void contextLoads() {
	}

}

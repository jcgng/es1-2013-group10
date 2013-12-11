package moodish.securityfilter.testsuite;

import static org.junit.Assert.*;
import moodish.securityfilter.records.models.SecurityFilterClient;

import org.junit.Test;

public class SecurityFilterClientTestCase {

	// TODO: End this test
	
	@Test
	public void testSecurityFilterClient() {
		SecurityFilterClient securityFilterClient = new SecurityFilterClient("NickFury","OneEyedBlind");
		securityFilterClient.addClientFilterMood("OneEyedBlind");
		assertFalse(securityFilterClient.checkClientLastMoods("OneEyedBlind", 60, 3));
		securityFilterClient.addClientFilterMood("OneEyedBlind");
		assertTrue(securityFilterClient.checkClientLastMoods("OneEyedBlind", 60, 3));

		
		securityFilterClient = new SecurityFilterClient("DoctorManhattan","FeelingBlue");
		securityFilterClient.addClientFilterMood("Glowing");
		assertFalse(securityFilterClient.checkClientLastMoods("Glowing", 60, 3));
		securityFilterClient.addClientFilterMood("FeelingBlue");
		assertFalse(securityFilterClient.checkClientLastMoods("FeelingBlue", 60, 3));

		securityFilterClient.addClientFilterMood("FeelingBlue");
		securityFilterClient.addClientFilterMood("Glowing");
		securityFilterClient.addClientFilterMood("FeelingBlue");
		assertFalse(securityFilterClient.checkClientLastMoods(30, 10));
		securityFilterClient.addClientFilterMood("Glowing");
		securityFilterClient.addClientFilterMood("FeelingBlue");
		securityFilterClient.addClientFilterMood("Glowing");
		assertFalse(securityFilterClient.checkClientLastMoods(30, 10));
		securityFilterClient.addClientFilterMood("FeelingBlue");
		assertTrue(securityFilterClient.checkClientLastMoods(30, 10));
	}
}

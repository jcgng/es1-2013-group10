package moodish.securityfilter.testsuite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith; 
import org.junit.runners.Suite;

@RunWith(Suite.class) 
@Suite.SuiteClasses({
	SecurityFilterMoodBufferTestCase.class,
	SecurityFilterClientTestCase.class,
	MoodishSecurityFilterTestCase.class
})
public class MoodishSecurityFilterTestSuite {
	@BeforeClass public static void setUpClass() {
        // Common initialization done once
    }
    @AfterClass public static void tearDownClass() {
        // Common cleanup for all tests
    }
}

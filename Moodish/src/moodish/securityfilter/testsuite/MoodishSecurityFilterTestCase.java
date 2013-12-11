package moodish.securityfilter.testsuite;

import static org.junit.Assert.*;

import moodish.comm.ServerComm;
import moodish.comm.ServerSideMessage;
import moodish.dummy.ServerCommDummy;
import moodish.securityfilter.MoodishSecurityFilter;

import org.junit.Test;

public class MoodishSecurityFilterTestCase {
	private int timeBetweenMsg = 30000;
	private ServerComm srvComm = new MoodishSecurityFilter(new ServerCommDummy());

	// TODO: Capture Stream
	
	@Test
	public void testGetNextMessage() {	
		while(srvComm.hasNextMessage()) {
			ServerSideMessage srvMsg = srvComm.getNextMessage();
			try {
				Thread.sleep(timeBetweenMsg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

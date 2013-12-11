package moodish.securityfilter.testsuite;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import moodish.comm.ServerComm;
import moodish.comm.ServerSideMessage;
import moodish.dummy.ServerCommDummy;
import moodish.securityfilter.MoodishSecurityFilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MoodishSecurityFilterTestCase {
//	private final ByteArrayOutputStream outConsole = new ByteArrayOutputStream();
	
	private final int timeBetweenMsg1 = 1000;
	private final int timeForUnblock = 60000;
	
	private final int timeBetweenMsg2 = 30000;
	
	private ServerComm srvComm = new MoodishSecurityFilter(new ServerCommDummy());
	
//	@Before
//	public void setOutStreams(){
//		System.setOut(new PrintStream(outConsole,false));
//	
//	}
	
//	@After
//	public void cleanOutStreams(){
//		System.setOut(null);
//	}
	
	// TODO: Capture Standard Output Stream
	
	@Test
	public void testGetNextMessage() {	
		while(srvComm.hasNextMessage()) {
			@SuppressWarnings("unused")
			ServerSideMessage srvMsg = srvComm.getNextMessage();
			try {
				Thread.sleep(timeBetweenMsg1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(timeForUnblock);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(srvComm.hasNextMessage()) {
			@SuppressWarnings("unused")
			ServerSideMessage srvMsg = srvComm.getNextMessage();
			try {
				Thread.sleep(timeBetweenMsg2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

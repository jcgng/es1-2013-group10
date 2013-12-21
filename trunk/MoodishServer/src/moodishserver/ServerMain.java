package moodishserver;

import moodishcomm.comm.server.MyServerComm;
import moodishsecurity.securityfilter.MoodishSecurityFilter;
import moodishserver.server.MyMoodishServer;

public class ServerMain {	
	public static void main(String[] args) {
		MyMoodishServer server = new MyMoodishServer();
		server.start(new MoodishSecurityFilter(new MyServerComm()));
	}
}

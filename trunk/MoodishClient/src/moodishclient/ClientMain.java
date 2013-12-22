package moodishclient;

import java.io.IOException;
import java.net.UnknownHostException;

import moodishclient.client.MoodishClient;
import moodishcomm.comm.ClientComm;
import moodishcomm.comm.client.MyClientComm;

public class ClientMain {	
	public static void main(String[] args) {
		ClientComm clientComm = new MyClientComm();
		MoodishClient  client = new MyMoodishClient();
		client.start(clientComm);
		
		// Group 10: test server client comm
//		int count = 0;
//		try {
//			clientComm.connect("127.0.0.1", "teste");
//			while(clientComm.isConnected()) {
//				clientComm.sendMoodishMessage("Cheated" + count++);
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		} catch (UnknownHostException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}
}

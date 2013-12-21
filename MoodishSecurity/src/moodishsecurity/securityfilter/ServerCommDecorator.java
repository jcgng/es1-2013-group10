package moodishsecurity.securityfilter;

import moodishcomm.comm.ServerComm;
import moodishcomm.comm.ServerSideMessage;

/**
 * ServerComm decorator Class 
 * 
 * @author Default/Group 10
 * @version 1
 */
public class ServerCommDecorator implements ServerComm {
	protected ServerComm serverComm;
	
	public ServerCommDecorator(ServerComm serverComm) {
		this.serverComm = serverComm;
	}
	
	protected ServerComm getServerComm() {
		return serverComm;
	}
	
	@Override
	public void start() {
		serverComm.start();
	}

	@Override
	public ServerSideMessage getNextMessage() {
		return serverComm.getNextMessage();
	}

	@Override
	public boolean hasNextMessage() {
		return serverComm.hasNextMessage();
	}

	@Override
	public void sendMoodishMessage(String fromNickname, String toNickname, String moodishMessage) {
		serverComm.sendMoodishMessage(fromNickname,toNickname,moodishMessage);
	}

	@Override
	public void sendNewFriendship(String toNickname, String newFriendship) {
		serverComm.sendNewFriendship(toNickname,newFriendship);
	}

	@Override
	public void sendNewUnfriendship(String toNickname, String unfriendship) {
		serverComm.sendNewUnfriendship(toNickname, unfriendship);
	}

	@Override
	public void sendError(String toNickname, String error) {
		serverComm.sendError(toNickname, error);
	}

	@Override
	public void sendClientConnected(String toNickname, String connectedNickname) {
		serverComm.sendClientConnected(toNickname, connectedNickname);
	}

	@Override
	public void sendClientDisconnected(String toNickname, String disconnectedNickname) {
		serverComm.sendClientDisconnected(toNickname, disconnectedNickname);
	}

	@Override
	public boolean clientIsConnected(String nickname) {
		return serverComm.clientIsConnected(nickname);
	}

	@Override
	public void disconnectClient(String nickname) {
		serverComm.disconnectClient(nickname);
	}
}

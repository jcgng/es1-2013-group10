package moodish.dummy;

import moodish.comm.ServerComm;
import moodish.comm.ServerSideMessage;

public class ServerCommDummy implements ServerComm {
	
	
	@Override
	public void start() {
		
	}

	@Override
	public boolean hasNextMessage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ServerSideMessage getNextMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendNewFriendship(String toNickname, String newFriendship) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendError(String toNickname, String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean clientIsConnected(String nickname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendClientConnected(String toNickname, String userConnected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendClientDisconnected(String toNickname, String userDisconnected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnectClient(String nickname) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sendMoodishMessage(String fromNicename, String toNickname,
			String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNewUnfriendship(String toNickname, String newFriendship) {
		// TODO Auto-generated method stub
		
	}
}

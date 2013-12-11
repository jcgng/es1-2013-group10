package moodish.dummy;

import moodish.comm.ServerComm;
import moodish.comm.ServerSideMessage;
import moodish.comm.ServerSideMessage.Type;

public class ServerCommDummy implements ServerComm {
	private final ServerSideMessageDummy[] messagesDummy = {
		new ServerSideMessageDummy(Type.CLIENT_CONNECTED,"NickFury","CONNECT!"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"NickFury","OneEyedBlind"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"NickFury","OneEyedBlind"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"NickFury","OneEyedBlind"),
		new ServerSideMessageDummy(Type.CLIENT_CONNECTED,"DoctorManhattan","CONNECT!"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","FeelingBlue"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","Glowing"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","FeelingBlue"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","Glowing"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","FeelingBlue"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","Glowing"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","FeelingBlue"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","Glowing"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","FeelingBlue"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","Glowing"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","FeelingBlue"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","Glowing"),
		new ServerSideMessageDummy(Type.MOODISH_MESSAGE,"DoctorManhattan","FeelingBlue"),
		new ServerSideMessageDummy(Type.CLIENT_CONNECTED,"NickFury","CONNECT!"),
		new ServerSideMessageDummy(Type.CLIENT_CONNECTED,"DoctorManhattan","CONNECT!"),
	};
	private int nextMsgIndexDummy = 0;
	
	@Override
	public void start() { }

	@Override
	public boolean hasNextMessage() {
		return nextMsgIndexDummy < messagesDummy.length;
	}

	@Override
	public ServerSideMessage getNextMessage() {
		return messagesDummy[nextMsgIndexDummy++];
	}

	@Override
	public void sendError(String toNickname, String error) {
		System.out.println("Error: " + toNickname + " " + error);
	}

	@Override
	public void disconnectClient(String nickname) {
		System.out.println("DISCONNECT: " + nickname);
	}
	
	@Override
	public void sendNewFriendship(String toNickname, String newFriendship) {
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
	public void sendMoodishMessage(String fromNicename, String toNickname,String message) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sendNewUnfriendship(String toNickname, String newFriendship) {
		// TODO Auto-generated method stub
	}
}

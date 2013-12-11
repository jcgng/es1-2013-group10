package moodish.dummy;

import moodish.comm.ServerSideMessage;

public class ServerSideMessageDummy implements ServerSideMessage {
	private Type type;
	private String payload;
	private String nickname;
	
	public ServerSideMessageDummy(Type type, String nickname, String payload) {
		this.type = type;
		this.payload = payload;
		this.nickname = nickname;
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getPayload() {
		return payload;
	}

	@Override
	public String getClientNickname() {
		return nickname;
	}

}

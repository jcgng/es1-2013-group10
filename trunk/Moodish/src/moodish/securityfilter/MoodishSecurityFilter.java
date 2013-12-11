package moodish.securityfilter;

import moodish.comm.ServerComm;
import moodish.comm.ServerSideMessage;
import moodish.comm.ServerSideMessage.Type;

import moodish.securityfilter.exceptions.SecurityFilterClientBlockException;
import moodish.securityfilter.records.SecurityFilterClients;

/**
 * Security Filter for the Moodish Server Communications interface.</br>
 * Example: ServerComm srvComm = new MoodishSecurityFilter(new ServerCommDummy());
 * 
 * @author Default/Group 10
 * @version 1
 */
public class MoodishSecurityFilter extends ServerCommDecorator {	
	private static final int firstTimeSpan = 30; // in seconds
	private static final int secondTimeSpan = 60; // in seconds
	private static final int blockedClientTimeSpan = 60; // in seconds

	private enum Actions {
		DO_NOTHING,
		
		/**
		 * Send the <i>FIRST_WARNING</i> warning message
		 * to the client.
		 */
		SEND_FIRST_WARNING,
		
		/**
		 * Send the <i>SECOND_WARNING</i> warning message
		 * to the client.
		 */
		SEND_SECOND_WARNING,
		
		/**
		 * Disconnect the client from the server.
		 */
		DISCONNECT_CLIENT,
		
		/**
		 * Block the client from server access.
		 */
		BLOCK_CLIENT
	};
	
	private enum Messages {
	    FIRST_WARNING("WARNING: You repeatedly changed mood 8 times within a span of "+firstTimeSpan+" seconds!"),
	    SECOND_WARNING("WARNING: You repeatedly changed to same mood twice within a span of "+secondTimeSpan+" seconds!"),
	    BLOCKED_CLIENT_MSG("WARNING: You're nickname is blocked for at least "+blockedClientTimeSpan+ " seconds, please try again later!");
	    
	    private final String msg;       
	    private Messages(String str) { msg = str; }
	    public String toString() { return msg; }
	};
	
	private SecurityFilterClients clientsReceivedMoods = new SecurityFilterClients();
	
	/**
	 * <i>MoodishSecurityFilter</i> Class contructor.
	 * 
	 * @param <b>serverComm</b> The ServerComm object.
	 */
	public MoodishSecurityFilter(ServerComm serverComm) {
		super(serverComm);
	}
	
	/**
	 * Moodish <i>ServerSideMessage</i> security filter rules.
	 * 
	 * @param <b>serverSideMessage</b> The received server side message.
	 * @return The action to be performed by the server.
	 */
	private Actions filterRules(ServerSideMessage serverSideMessage) {
		String nickname = serverSideMessage.getClientNickname();
		String payload = serverSideMessage.getPayload();
		try {
			if(serverSideMessage.getType() == Type.MOODISH_MESSAGE) { // is moodish?

				clientsReceivedMoods.addReceivedMood(nickname,payload);
				
				if(clientsReceivedMoods.checkClientLastMoods(nickname, firstTimeSpan, 10)) {
					clientsReceivedMoods.blockClient(nickname);
					return Actions.DISCONNECT_CLIENT;
				} else {
					if(clientsReceivedMoods.checkClientLastMoods(nickname, firstTimeSpan, 8))
						return Actions.SEND_FIRST_WARNING;
				}
				
				if(clientsReceivedMoods.checkClientLastMoods(nickname, payload, secondTimeSpan, 3)) {
					clientsReceivedMoods.blockClient(nickname);
					return Actions.DISCONNECT_CLIENT;
				} else {
					if(clientsReceivedMoods.checkClientLastMoods(nickname, payload, secondTimeSpan, 2))
						return Actions.SEND_SECOND_WARNING;
				}
			} else if(serverSideMessage.getType() == Type.CLIENT_CONNECTED) { // is connected?
				Long timeLapsed = clientsReceivedMoods.blockedClientTimeLapsed(nickname);
				if(timeLapsed!=null) {
					if(timeLapsed.longValue()<blockedClientTimeSpan) {
						return Actions.BLOCK_CLIENT;
					} else {
						clientsReceivedMoods.unblockClient(nickname);
					}
				}
			}
		} catch(SecurityFilterClientBlockException ex) {
			// TODO: Print to log
			System.out.println(ex.getMessage());
		}
		return Actions.DO_NOTHING;
	}

	@Override
	public ServerSideMessage getNextMessage() {
		ServerSideMessage serverSideMessage = getServerComm().getNextMessage(); // wait for message
		Actions filterActions = filterRules(serverSideMessage);
		switch(filterActions) {
			case SEND_FIRST_WARNING:
				sendError(serverSideMessage.getClientNickname(),Messages.FIRST_WARNING.toString());
				break;
			case SEND_SECOND_WARNING:
				sendError(serverSideMessage.getClientNickname(),Messages.SECOND_WARNING.toString());
				break;
			case BLOCK_CLIENT:
				sendError(serverSideMessage.getClientNickname(),Messages.BLOCKED_CLIENT_MSG.toString());
			case DISCONNECT_CLIENT:
				disconnectClient(serverSideMessage.getClientNickname());
				break;
		}
		return serverSideMessage;
	}
}

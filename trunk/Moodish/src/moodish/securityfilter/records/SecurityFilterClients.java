package moodish.securityfilter.records;

import java.util.HashMap;

import moodish.securityfilter.exceptions.SecurityFilterClientBlockException;
import moodish.securityfilter.records.models.SecurityFilterClient;

/**
 * Class that stores the clients that have sent a mood message to the server.
 * 
 * @author Default/Group 10
 * @version 1
 */
public class SecurityFilterClients {
	private HashMap<String,SecurityFilterClient> clients = new HashMap<String,SecurityFilterClient>();
	
	/**
	 * Get stored client by nickname.
	 * 
	 * @param <b>nickname</b> The client nickname.
	 * @return <b>SecurityFilterClient</b> if the client stored, otherwise <b>null</b>
	 */
	private SecurityFilterClient getClient(String nickname) {
		return clients.get(nickname);
	}
	
	/**
	 * Add received mood a the client.
	 * 
	 * @param <b>nickname</b> The client nickname.
	 * @param <b>mood</b> The mood that will be added to the client.
	 */
	public void addReceivedMood(String nickname,String mood) {
		if(clients.containsKey(nickname))
			getClient(nickname).addClientFilterMood(mood);
		else 
			clients.put(nickname, new SecurityFilterClient(nickname,mood));
	}
	
	/**
	 * Check if client's last received <i>numMoods</i> moods are inside the time interval.
	 * 
	 * @param <b>timeLapse</b> The time interval.
	 * @param <b>numMoods</b> Number of moods to be checked.
	 * @return <b>true</b> if the last received <i>numMoods</i> are inside the time interval, otherwise <b>false</b>.
	 */
	public boolean checkClientLastMoods(String nickname, int timeLapse, int numMoods) {
		return getClient(nickname).checkClientLastMoods(timeLapse,numMoods);
	}
	
	/**
	 * Check if client's last received <i>numMoods</i> moods are inside the time interval.
	 * 
	 * @param <b>timeLapse</b> The time interval.
	 * @param <b>numMoods</b> Number of moods to be checked.
	 * @return <b>true</b> if the last received <i>numMoods</i> are inside the time interval, otherwise <b>false</b>.
	 */
	public boolean checkClientLastMoods(String nickname, String mood, int timeLapse, int numMoods) {
		return getClient(nickname).checkClientLastMoods(mood,timeLapse,numMoods);
	}
	
	/**
	 * Block a client to access/connect to the server.
	 * 
	 * @param <b>nickname</b> The client nickname.
	 * @throws <b>SecurityFilterClientBlockException</b> if client is not stored.
	 */
	public void blockClient(String nickname) throws SecurityFilterClientBlockException {
		SecurityFilterClient client = getClient(nickname);
		if(client!=null) 
			client.blockClient();
		else 
			throw new SecurityFilterClientBlockException("Unable to block client - there is no client with nickname " + nickname);
	}
	
	/**
	 * Unblock a client to access/connect to the server.
	 * 
	 * @param <b>nickname</b> The client nickname.
	 * @throws <b>SecurityFilterClientBlockException</b> if client is not stored.
	 */
	public void unblockClient(String nickname) throws SecurityFilterClientBlockException {
		SecurityFilterClient client = getClient(nickname);
		if(client!=null)
			client.unblockClient();
		else
			throw new SecurityFilterClientBlockException("Unable to unblock client - there is no client with nickname " + nickname);
	}
	
	/**
	 * Get the time that passed since a client was blocked.
	 * 
	 * @param <b>nickname</b> The client nickname.
	 * @return <b>time lapsed</b> if client is blocked, otherwise <b>null</b> 
	 */
	public Long blockedClientTimeLapsed(String nickname) {
		SecurityFilterClient client = getClient(nickname);
		if(client==null) return null;
		else return getClient(nickname).blockTimeLapsed();
	}
}

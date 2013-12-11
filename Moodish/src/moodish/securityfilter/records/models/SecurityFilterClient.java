package moodish.securityfilter.records.models;

import moodish.securityfilter.records.buffer.SecurityFilterMoodBuffer;

/**
 * Class with the relevant client information for the filter.
 * 
 * @author Default/Group 10
 * @version 1
 */
public class SecurityFilterClient {
	private String nickname;
	private Boolean isBlocked = false;
	private Long blockTimestamp = null;
	
	private SecurityFilterMoodBuffer clientLastMoods = new SecurityFilterMoodBuffer(11);
	
	/**
	 * <i>SecurityFilterClient</i> Class constructor.
	 * 
	 * @param <b>nickname</b> The client nickname.
	 * @param <b>mood</b> The received client mood.
	 */
	public SecurityFilterClient(String nickname,String mood) {
		this.nickname = nickname;
		clientLastMoods.insert(new SecurityFilterMood(mood));
	}

	/**
	 * Get the filtered client nickname.
	 * 
	 * @return The client nickname.
	 */
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * Get the time stamp when the filtered client was blocked.
	 * 
	 * @return <b>Block time stamp</b> if filtered client is blocked, otherwise <b>null</b>.
	 */
	public Long blockTimestamp() {
		return blockTimestamp;
	}
	
	/**
	 * Calculate and return the time lapsed since the filtered client
	 * was blocked.
	 * 
	 * @return <b>Block time lapsed</b> if filtered client is blocked, otherwise <b>null</b>. 
	 */
	public Long blockTimeLapsed() {
		if(blockTimestamp == null) return null; 
		else return (Long)((System.currentTimeMillis()/1000) - blockTimestamp.longValue());
	}
	
	/**
	 * Block the client from server access.
	 */
	public void blockClient() {
		isBlocked = true;
		blockTimestamp = (Long) System.currentTimeMillis()/1000;
	}
	
	/**
	 * Unblock the client from server access.
	 */
	public void unblockClient() {
		isBlocked = false;
		blockTimestamp = null;
	}
	
	/**
	 * Check if the client is blocked.
	 * 
	 * @return <b>true</b> if client is blocked, otherwise <b>false</b>.
	 */
	public boolean isBlocked() {
		return isBlocked;
	}
	
	/**
	 * Get client last received mood.
	 * 
	 * @return <b>SecurityFilterMood</b> the last received mood.
	 */
	public SecurityFilterMood getClientLastMood() {
		return clientLastMoods.getLast();
	}
	
	/**
	 * Add to client a received mood.
	 * 
	 * @param <b>mood</b> the client received mood.
	 */
	public void addClientFilterMood(String mood) {
		clientLastMoods.insert(new SecurityFilterMood(mood));
	}
	
	/**
	 * Check if client's last received <i>numMoods</i> moods are inside the time interval.
	 * 
	 * @param <b>timeLapse</b> The time interval.
	 * @param <b>numMoods</b> Number of moods to be checked.
	 * @return <b>true</b> if the last received <i>numMoods</i> are inside the time interval, otherwise <b>false</b>.
	 */
	public boolean checkClientLastMoods(long timeLapse, int numMoods) {
		SecurityFilterMood securityFilterMood = clientLastMoods.getLast(/*jumps*/numMoods-1);
		if(securityFilterMood!=null && securityFilterMood.getTimeLapsed()<=timeLapse) 
			return true;
		else 
			return false;
	}
	
	/**
	 * Check if client's last received <i>numMoods</i> moods are equal to <i>mood</i> and 
	 * inside the time interval.
	 * 
	 * @param <b>mood</b> The mood to be checked.
	 * @param <b>timeLapse</b> The time interval.
	 * @param <b>numMoods</b> Number of moods to be checked.
	 * @return <b>true</b> if the last received <i>numMoods</i> are inside the time interval, otherwise <b>false</b>.
	 */
	public boolean checkClientLastMoods(String mood, long timeLapse, int numMoods) {
		int iterations = 0;
		int count = 0;
		for(SecurityFilterMood securityFilterMood : clientLastMoods) {
			if(securityFilterMood.getTimeLapsed().longValue()<=timeLapse && mood.equals(securityFilterMood.getMood())) count++;
			else break;
			if(iterations++ > numMoods) break;
		}
		if(count<numMoods) return false;
		else return true;
	}
}

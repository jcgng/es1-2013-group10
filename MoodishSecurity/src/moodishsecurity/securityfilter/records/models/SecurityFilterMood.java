package moodishsecurity.securityfilter.records.models;

/**
 * Class with the relevant security received Mood for the security filter.
 * 
 * @author Default/Group 10
 * @version 1
 */
public class SecurityFilterMood {
	private String mood;
	private Long moodTimestamp = null;
	
	/**
	 * <i>SecurityFilterMood</i> Class constructor.
	 * 
	 * @param <b>mood</b> The client received mood.
	 */
	public SecurityFilterMood(String mood) {
		this.mood = mood;
		moodTimestamp = (Long) System.currentTimeMillis()/1000;
	}
	
	/**
	 * Get the received mood.
	 * 
	 * @return saved mood.
	 */
	public String getMood() {
		return mood;
	}

	/**
	 * Get the time that passed since mood was received.
	 * 
	 * @return Time lapsed.
	 */
	public Long getTimeLapsed() {
		if(moodTimestamp == null) 
			return null;
		else 
			return ((System.currentTimeMillis()/1000) - moodTimestamp.longValue());
	}
}

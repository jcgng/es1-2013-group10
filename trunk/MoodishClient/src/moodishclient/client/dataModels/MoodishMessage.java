package moodishclient.client.dataModels;

import moodishclient.client.states.Mood;

/**
 * Created with IntelliJ IDEA.
 * User: J-Pac123
 * Date: 12/8/13
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class MoodishMessage {

    private String message;

    public MoodishMessage(Mood moodMessage) {
        message = Mood.lookupValue(moodMessage);
    }

    public String getMessage() {
        return message;
    }
}



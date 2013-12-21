package moodishclient.client.dataModels;

import moodishclient.client.states.UserSessionState;
import moodishclient.client.views.viewComponents.ConversationView;
import moodishclient.client.views.viewComponents.FriendInformation;

import java.util.HashMap;
import java.util.Map;
	/**
	 *  Class that defines the user, and existing users
	 *
	 * @author Jos? Costa
	 * @author Luis Pires
	 * @author Sonia Morais
	 */

public class UserData {

        private String nickname;
        private UserSessionState sessionState;

        private String lastMood;

        public String getLastMood() {
            return lastMood;
        }

        public void setLastMood(String lastMood) {
            this.lastMood = lastMood;
        }

        private Map<String, FriendInformation> connectedFriends = new HashMap<String, FriendInformation>();
        private Map<String, ConversationView> currentOpenedConversations = new HashMap<String, ConversationView>();
	/**
	 *
	 * The new User is created as the Disconnected state
	 */
    public UserData() {
        sessionState = UserSessionState.DISCONNECTED;
    }
    /**
     * returns the nickname of the user
     * @return nickname
     */

    public String getNickname() {
        return nickname;
    }
    /**
     * returns the status of the user
     * @return sessionState
     */

    public UserSessionState getSessionState() {
        return sessionState;
    }
    /**
     * User status changes
     * @param sessionState
     */
    public void setSessionState(UserSessionState sessionState) {
        this.sessionState = sessionState;
    }
    /**
     * Change nickname of the user
     * @param nickname
     */

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public FriendInformation getConnectedFriend(String friendNickname) {
        return connectedFriends.get(friendNickname);
    }

    /**
     * add a new "friend" the connecteFriends friends
     * @param friendInformation
     */

    public void addConnectedFriend(FriendInformation friendInformation) {
        connectedFriends.put(friendInformation.getFriendNickname(), friendInformation);
    }

    /**
     * remove the "friend" of connectedFriends
     * @param friendNickname
     */
    public void removeConnectedFriend(String friendNickname) {
        connectedFriends.remove(friendNickname);
    }
    /**
     * Returns the conversation a certain frienUsername
     * @param friendUsername
     * @return Return the private conversation Username friends
     */
    public ConversationView getOpenConversation(String friendUsername) {
        return currentOpenedConversations.get(friendUsername);
    }
    /**
     * add a conversation to conversation list
     *
     * @param friendUsername
     * @param conversation
     */
    public void openConversation(String friendUsername, ConversationView conversation) {
        currentOpenedConversations.put(friendUsername, conversation);
    }

    /**
     * identify whether a user corresponds to the expected
     * @param friendUsername
     * @return false or true
     */


    public Map<String, ConversationView> getOpenConversations() {
        return currentOpenedConversations;
    }

    public boolean isConversationOpened(String friendUsername) {
        return currentOpenedConversations.containsKey(friendUsername);
    }
    /**
     * close conversation of the friendUsername
     *
     * @param friendUsername
     */
    public void closeConversation(String friendUsername) {
        currentOpenedConversations.remove(friendUsername);
    }

    /**
     * if nickmaen is null, the system discards
     */
    public void clean() {
        nickname = null;
        sessionState = UserSessionState.DISCONNECTED;
        connectedFriends.clear();
        currentOpenedConversations.clear();
    }


}

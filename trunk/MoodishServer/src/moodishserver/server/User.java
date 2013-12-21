package moodishserver.server;


import java.util.LinkedList;

/**
 * User to use in the Moodish. The user had a nick name and a ListOffriends 
 * 
 * @author Joao / Ricardo
 *
 */

public class User {

	LinkedList<String> listOffriends;
	

	String user;
	Boolean isConnected = false;

	/**
	 * Constructor, it construct a new User and create an empty ListOffriends  for the User
	 * @param user  The Nickname of the User
	 */
	public User(String user) {

		this.user = user;

		listOffriends = new LinkedList<String>();
		
	}

	/**
	 * Get the Nickname from the User
	 * @return the user name
	 */
	public String getUser(){

		return user;
	}

	/**
	 * Get the list of friends 
	 * @return listOffriends
	 */

	public LinkedList<String> getlistOffriends() {
		return listOffriends;
	}

	/**
	 * Add a new nickname to the user list of friends 
	 * @param nick   nickname to had to add to the list
	 */
	public void addfriend(String nick){
		listOffriends.add(nick);
	}



	/**
	 * Removes a friend from his list 
	 * @param nick nickname to remove
	 */
	public void removeFriend(String nick){

		listOffriends.remove(nick);
	}


	/**
	 * Removes all friends from the list 
	 */
	public void removeAllFriend(){
		listOffriends.clear();
	}

	/**
	 * Checks if the user is connect
	 * @return <b>true</b> if a client with the nickname is currently connected, otherwise <b>false</b>.
	 */
	public Boolean isConnected (){

		return isConnected;
	}


	/**
	 * Set the user as Connected 
	 */
	public void setConnected(){

		isConnected = true;
	}


	/**
	 * Set the user as Disconnected 
	 */
	public void setDisconnect(){

		isConnected = false;
	}

	/**
	 * Return the user name as a String
	 */
	@Override
	public String toString() {
		return user;
	}

	/**
	 * Checks if the nickname is already in the list of friends 
	 * @param payload  nickname of the user to check
	 * @return
	 */
	public boolean inTheListofFriends(String payload) {

		for(String l :listOffriends){
			if(l.equals(payload))
				return true;
		}
		return false;
	}


	/**
	 * Checks if list of friends is empty.
	 * @return <b>true</b> if a list is empty, otherwise <b>false</b>.
	 */
	public boolean islistOffriendsEmpty(){
		if (listOffriends.isEmpty())
			return true;
		return false;
	}





}
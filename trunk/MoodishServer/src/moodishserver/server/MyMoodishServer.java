package moodishserver.server;

import java.util.ArrayList;
import java.util.LinkedList;

import moodishcomm.comm.ServerComm;
import moodishcomm.comm.ServerSideMessage;
import moodishserver.server.MoodishServer;

/**
 * The server that:  keep track of who follows who and forward Mood messages accordingly; 
 * keep clients informed about who friends and unfriends them;
 * when a new client connects, the server send the list of currently connected user to that client so that the client will have an updated list of all currently connected users;
 * when a client connects or disconnects, the server sends a message with the appropriate information to all connected clients
 * @author Joao / Ricardo
 *
 */
public class MyMoodishServer implements MoodishServer,Runnable {
	private ServerComm serverComm;
	ArrayList<User> listOfUsers = new ArrayList<User>();

	@Override
	public void start(ServerComm serverComm) {
		System.out.println("Server Started!");
		this.serverComm = serverComm;
		// start server thread
		Thread server = new Thread(this);
		server.start();
		// start serverComm
		this.serverComm.start();
	}
	
	@Override
	public void run() { // Group 10: server thread to process messages
		System.out.println("Server waiting for client messages!");
		while (true) {
			if (serverComm.hasNextMessage()){
				ServerSideMessage message = serverComm.getNextMessage();
				if(message == null) continue;
				/**
				 * When a client connects, the server send one {@link moodish.comm.ClientSideMessage.Type#CONNECTED CONNECTED} message 
				 * ({@link moodish.comm.ServerComm#sendClientConnected(String toNickname, String connectedNickname)
				 * ServerComm.sendClientConnected()}) for each connected client so that the newly connected client can maintain a list 
				 * of clients currently connected to the server. 
				 */
				if (message.getType() == ServerSideMessage.Type.CLIENT_CONNECTED){
					System.out.println("Client Connected!");
					boolean tempIsRegisteredUser = false;
					// Verifies if is registered user and if is, set him connected 
					for (User a: listOfUsers){
						if (a.getUser().equals(message.getClientNickname())){
							tempIsRegisteredUser = true;
							a.setConnected();
						
							LinkedList<String> listtemp = new LinkedList<String>();
							listtemp=a.getlistOffriends();
							
							for (String b: listtemp) {
								serverComm.sendClientConnected(a.getUser(), b.toString());
							}
						}
					}
					//If not registered, create a new User and sets Connected
					if (tempIsRegisteredUser == false){
						listOfUsers.add(new User(message.getClientNickname()));
						System.out.println("SERVER -->Just created a new User " + message.getClientNickname());
						for (User u:listOfUsers){
							if (u.getUser().equals(message.getClientNickname()))
								u.setConnected();
						}
						// send to client list of users connected
						for (User u: listOfUsers){
							if (u.getUser().equals(message.getClientNickname())){
								for (User x :listOfUsers){
									if(x.isConnected() && !u.getUser().equals(x.getUser()))
										serverComm.sendClientConnected(u.getUser(), x.getUser());
								}
							}
						}
					}
					//	Sends an sendClientConnected to all friends that are connected to the servers
					for (User u: listOfUsers){
						if (u.getUser().equals(message.getClientNickname())){
							LinkedList<String> listtemp = new LinkedList<String>();
							listtemp=u.getlistOffriends();
							for (String b: listtemp) {
								serverComm.sendClientConnected(b.toString(), message.getClientNickname());
							}
						}
					}	
				}
				
				/**
				 * 
				 *<p>
				 * When a client disconnects, all clients should be sent a {@link moodish.comm.ClientSideMessage.Type#DISCONNECTED DISCONNECTED} message 
				 * ({@link moodish.comm.ServerComm#sendClientDisconnected(String toNickname, String disconnectedNickname)
				 * ServerComm.sendClientDisconnected()}). When a client disconnects, it loses all its friends and automatically
				 * unfreinds all other clients. The server should thus not maintain a memory server regarding friends of disconnected clients. 
				 */
				else if (message.getType() == ServerSideMessage.Type.CLIENT_DISCONNECTED) { //checks if the message is type CLIENT_DISCONNECTED
					System.out.println("Client Disconnected!");
					for (User u: listOfUsers){
						if (u.isConnected() && !u.getUser().equals(message.getClientNickname()))
							serverComm.sendClientDisconnected(u.getUser(),message.getClientNickname()); 
					}	
					// sets the user as disconnected and removes all list
					for (User a:listOfUsers){
						if (a.getUser().equals(message.getClientNickname())){
							a.setDisconnect();
							System.out.println(" SERVER-->The user " + message.getClientNickname() + " is disconnecting");
							a.removeAllFriend();
						}	
					}
				} else if (message.getType().equals(ServerSideMessage.Type.MOODISH_MESSAGE)) { // checks if the message is type MOODISH_MESSAGE
					System.out.println("Moodish Message!");
					LinkedList<String> tempFriends = new LinkedList<String>();
					// return the list of Friends
					for (User a: listOfUsers){
						if (a.getUser().equals(message.getClientNickname())){
							tempFriends = a.getlistOffriends();				
							for (String b: tempFriends) {
								// sends to all of his followers the message
								serverComm.sendMoodishMessage(message.getClientNickname(), b.toString(), message.getPayload());
								System.out.println("Lista de utilizadores " + b.toString());				
							}
						}
					}			
				} else if (message.getType().equals(ServerSideMessage.Type.FRIENDSHIP)){ // checks if the message is type
					System.out.println("Friendship Request!");
					for (User a: listOfUsers){
						if (a.getUser().equals(message.getClientNickname())){
						//If not in his list of Following, it add him to the list and sends a sendNewFollowers message.
						if (!a.inTheListofFriends(message.getPayload())){ 
							a.addfriend(message.getPayload());
							serverComm.sendNewFriendship(message.getPayload(), message.getClientNickname());
						} else
							//if in the list, it sends one error message
							serverComm.sendError(message.getClientNickname(), " Already following the requested user");
						}
					}
				} else if (message.getType() == ServerSideMessage.Type.UNFRIENDSHIP){ //checks if the message is type UNFRIENDSHIP
					System.out.println("Unfriendship Request!");
					for (User a: listOfUsers){
						if (a.getUser().equals(message.getClientNickname())){
							//If is in the list of users he his following, it removes and sends a message sendNewUnfollow
							if(a.inTheListofFriends(message.getPayload())){
								a.removeFriend(message.getPayload());
								serverComm.sendNewUnfriendship(message.getPayload(), message.getClientNickname());
							} else {
								//Of not in the list, sends one error message
								serverComm.sendError(message.getClientNickname(), " Not following the requested user");
							}
						}
					}
				} else {
					System.out.println("Unexpected Message!");
					//If it sends one message that has no Type than the others, it sends one erros message
					serverComm.sendError(message.getClientNickname(), " Unexpected service request!");
				}
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

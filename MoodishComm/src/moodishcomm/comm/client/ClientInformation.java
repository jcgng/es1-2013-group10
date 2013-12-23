package moodishcomm.comm.client;

import java.io.ObjectOutputStream;

import moodishcomm.comm.server.MyServerComm.DealWithClient;

/**
 * 
 * @author marta
 * @version 1.1
 * Classe que representa a informaçao de um cliente, o seu nick e o canal de output associado
 *
 */
public class ClientInformation {
	private String nick;
	private ObjectOutputStream out;
	// Group 10
	private DealWithClient clientThread = null;
	
	/**
	 * @param nick nome do cliente
	 * @param out canal do cliente
	 */	
	public ClientInformation(String nick, ObjectOutputStream out, DealWithClient clientThread){
		this.nick = nick;
		this.out = out;
		this.clientThread = clientThread;
	}
	

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	// Group 10	
	public DealWithClient getClientThread() {
		return this.clientThread;
	}
}

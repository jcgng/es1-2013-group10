package moodishcomm.comm.client;

import java.io.ObjectOutputStream;

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

	/**
	 * @param nick nome do cliente
	 * @param out canal do cliente
	 */	
	public ClientInformation(String nick, ObjectOutputStream out){
		this.nick = nick;
		this.out = out;
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
}

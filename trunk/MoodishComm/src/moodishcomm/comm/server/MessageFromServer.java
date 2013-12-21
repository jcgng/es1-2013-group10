package moodishcomm.comm.server;

import java.io.Serializable;

import moodishcomm.comm.ClientSideMessage;

/**
 * 
 * @author joao
 * Classe que representa as mensagens vindas do Server
 * @version 1.2
 * @see Message
 *
 */

public class MessageFromServer implements ClientSideMessage,Serializable {
	private static final long serialVersionUID = 1L;
	
	private String sender;
	private String moodMessage;
	private String receiver;
	private ClientSideMessage.Type type;
	
	/**
	 * 
	 * @param sender nome de quem enviou a mensagem
	 * @param moodMessage conteudo da mensagem
	 * @param receiver nome do destino da mensagem
	 * @param type tipo da mensagem
	 * @see ClientSideMessage
	 */
	
	public MessageFromServer(String sender, String  moodMessage ,String receiver, Type type) {
		super();
		this.sender = sender;
		this.moodMessage = moodMessage;
		this.receiver = receiver;
		this.type = type;
	}

	@Override
	public String getPayload() {
		return moodMessage;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getSendersNickname() {
		return sender;
	}

	@Override
	public String toString() {
		return "MessageFromServer [sender=" + sender + ", moodMessage="
				+ moodMessage + ", receiver=" + receiver + ", type=" + type
				+ "]";
	}
}

package moodishcomm.comm.client;

import java.io.Serializable;

import moodishcomm.comm.ServerSideMessage;

/**
 * 
 * @author marta
 * @version 1.0
 * Classe que representa uma mensagem vinda do cliente
 * @see Message
 *
 */
public class MessageFromClient implements ServerSideMessage,Serializable {
	private static final long serialVersionUID = 1L;
	
	private String sender;
	private String moodMessage;
	private String receiver;
	private ServerSideMessage.Type type;
	
	/**
	 * 
	 * @param sender nome de quem enviou a mensagem
	 * @param moodMessage conteudo da mensagem
	 * @param receiver destino da mensagem
	 * @param type tipo
	 * @see ServerSideMessage
	 */
	public MessageFromClient(String sender, String  moodMessage ,String receiver, Type type) {
		super();
		this.sender = sender;
		this.moodMessage = moodMessage;
		this.receiver = receiver;
		this.type = type;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	@Override
	public String getPayload() {
		if(type == Type.MOODISH_MESSAGE)
			return moodMessage;
			return null;
	}
	@Override
	public String getClientNickname() {
		return sender;
	}

	@Override
	public String toString() {
		return "MessageFromClient [sender=" + sender + ", moodMessage="
				+ moodMessage + ", receiver=" + receiver + ", type=" + type
				+ "]";
	}
}

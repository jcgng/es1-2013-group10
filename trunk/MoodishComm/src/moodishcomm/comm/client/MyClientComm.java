package moodishcomm.comm.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import moodishcomm.comm.ClientComm;
import moodishcomm.comm.ClientSideMessage;
import moodishcomm.comm.server.MessageFromServer;

/**
 * 
 * @author luis
 * @version 1.0
 * A classe representa os objectos ClientCommDummy, implementado a interface ClientComm, esta é a class que faz a comunicaçao por parte dos clientes com o server
 */
public class MyClientComm implements ClientComm {
	private static final int PORT = 8088;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String nick;
	private Vector<MessageFromServer> filaDeMensagens = new Vector<MessageFromServer>();
	// Group 10: disconnect flag
	private boolean listenServer = true;
	
	/**
	 * @author luis
	 * @param ServerAddress nome do server 
	 * @param nickname é o nickname do user que se está a connectar
	 * Metodo que estabelece a ligaçao, criado a socket que e os canais da mesma
	 */
	@Override
	public void connect(String serverAddress, String nickname) throws IOException {
		InetAddress x = InetAddress.getByName(serverAddress);
		socket = new Socket(x, MyClientComm.PORT);
		this.nick = nickname;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		out.writeObject(new MessageFromClient(nick, null, null, moodishcomm.comm.ServerSideMessage.Type.CLIENT_CONNECTED));
		// Group 10: start listener thread
		new DealWithServer(socket).start();
	}

	/**
	 * @author luis
	 * Metodo que verifica se a socket esta fechada ou nao
	 * @return true se a socket nao estiver fechada
	 */
	@Override
	public boolean isConnected() {
		if(!socket.isClosed() && listenServer)
			return true;
		return false;
	}

	/**
	 * @author joao
	 * Metodo que manda uma mensagem do tipo "Client_Disconnected" e fecha a socket
	 * 
	 */
	@Override
	public void disconnect() {	
		try {
			out.writeObject(new MessageFromClient(nick, null, null, moodishcomm.comm.ServerSideMessage.Type.CLIENT_DISCONNECTED));
			socket.close();
			listenServer = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author joao
	 * Metodo que verifica se a filaDeMensagens nao se encontra vazia
	 * @return true se a fila de mensagens nao estiver vazia
	 */
	@Override
	public boolean hasNextMessage() {
		if(filaDeMensagens.size() != 0)
			return true;
		return false;
	}
	
	/**
	 * @author joao
	 * Metodo que retira e retorna a proxima mensagem que esteja na filaDeMensagens 
	 * @return {@link ClientSideMessage} caso existe alguma mensagem na fila, faz return (FI-FO)
	 * 
	 */
	@Override
	public ClientSideMessage getNextMessage() {
		while(filaDeMensagens.size() == 0) { // ver o problema que isto pode ter...
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 	
		if(filaDeMensagens.size() != 0) {
			MessageFromServer msg = filaDeMensagens.get(0);
			filaDeMensagens.remove(0); // possivelmente temos que criar um metodo sincronyzed
			return msg;
		}
		return  null;	
	}
	
	/**
	 * @author luis
	 * Metodo que envia uma mensagem para o canal do tipo Moodish
	 * @param moodishMessage conteudo da mensagem a enviar
	 * 
	 */
	@Override
	public void sendMoodishMessage(String moodishMessage) {
		try {
			out.writeObject(new MessageFromClient(nick, moodishMessage, null, moodishcomm.comm.ServerSideMessage.Type.MOODISH_MESSAGE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author luis
	 * Metodo que envia para o canal uma mensagem do tipo Friendship
	 * @param nickname nome a ser adicionado por este cliente á lsita de amigos
	 */
	@Override
	public void friendship(String nickname) {
		try {
			out.writeObject(new MessageFromClient(nick, null, nickname, moodishcomm.comm.ServerSideMessage.Type.FRIENDSHIP));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author luis
	 * Metodo que envia para o canal uma mensagem do tipo Unfreindship
	 * @param nickname nome a ser removido por este cliente da sua lista de amigos
	 */
	@Override
	public void unfriendship(String nickname) {
		try {
			out.writeObject(new MessageFromClient(nick, null, nickname, moodishcomm.comm.ServerSideMessage.Type.UNFRIENDSHIP));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @author joao
	 * @version 1.5
	 * 
	 */
	public class DealWithServer extends Thread{
		// Group 10
		private Socket socket;
		public DealWithServer(Socket socket){
			this.socket = socket;
		}
		
		/**
		 * @author joao
		 * Thread responsavel por ler mensagens do canal e escrever na fila de mensagens
		 */
		@Override
		public void run() {
//			super.run();
			while(listenServer && !this.socket.isClosed()){
				try {
					MessageFromServer msgServer = (MessageFromServer) in.readObject();
					System.out.println(msgServer);
					filaDeMensagens.add(msgServer); // possivelmente temos que fazer como no server criar um metodo sincronyzed
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
				} catch(EOFException e) {
//					e.printStackTrace();
					// Group 10: peer has ended connection
					listenServer = false;
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

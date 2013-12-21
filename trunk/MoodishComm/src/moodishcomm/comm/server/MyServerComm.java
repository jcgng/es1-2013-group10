package moodishcomm.comm.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import moodishcomm.comm.ClientSideMessage;
import moodishcomm.comm.ServerComm;
import moodishcomm.comm.ServerSideMessage;
import moodishcomm.comm.client.ClientInformation;
import moodishcomm.comm.client.MessageFromClient;

/**
 * 
 * @author joao
 * @version 1.1
 * Classe que representa a comunicaçao feita por parte do Server
 * @see ServerComm
 *
 */
public class MyServerComm implements ServerComm {
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Vector<MessageFromClient> filaDeMensagens = new Vector<MessageFromClient>();
	private Vector<ClientInformation> userList = new Vector<ClientInformation>();
	private static final int PORT = 8088;
	private ServerSocket s;
	// Group 10: client disconnect flag
	private boolean listenClient = true;
	
	/**
	 * @author joao
	 * @version 1.4
	 * Metodo que inicia a comunicaçao do servidor, liga a socket
	 * @see DealWithClient
	 */
	@Override
	public void start() {
		try {
			s = new ServerSocket(MyServerComm.PORT);
			while(true){
				new DealWithClient(s.accept()).start();
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @author joao
	 * Metodo que verifica se a filaDeMensagens nao se encontra vazia
	 * @return <code>true</code> se a fila de mensagens nao estiver vazia
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
	 * @return {@link ServerSideMessage} caso existe alguma mensagem na fila, faz return (FI-FO)
	 * 
	 */
	@Override
	public ServerSideMessage getNextMessage() {		
		while(filaDeMensagens.size() == 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(filaDeMensagens.size() != 0) {
			MessageFromClient msg = filaDeMensagens.get(0);
			removerMensagem();
			return msg;
		}
		return  null;
	}
	
	/**
	 * @author luis
	 * Metodo que remove uma mensagem da fila de mensagens, metodo é sincronizado para nao existir problemas de escrita na filaDeMensagens
	 * @see synchronized
	 */
	private synchronized void removerMensagem() {
		filaDeMensagens.remove(0);
	}

	/**
	 * @author luis
	 * Metodo que envia para o canal uma mensagem do tipo Friendship
	 * @param toNickname nome do cliente que vai receber a notificacao
	 * @param newFriendship nome do cliente que adicionou
	 * @see ClientSideMessage.Type
	 */
	@Override
	public void sendNewFriendship(String toNickname, String newFriendship) {
		try {
			for (int i = 0; i < userList.size(); i++) {
				if(userList.get(i).getNick() == toNickname)
					userList.get(i).getOut().writeObject(new MessageFromServer(newFriendship, null, toNickname, moodishcomm.comm.ClientSideMessage.Type.FRIENDSHIP));
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//O que estava antes
		/*try {
			out.writeObject(new MessageFromServer(newFriendship, null, toNickname, moodish.comm.ClientSideMessage.Type.FRIENDSHIP));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}
	
	/**
	 * @author luis
	 * Metodo que envia para o canal do cliente uma mensagem do tipo error
	 * @param toNickname nome do cliente que é suposto receber o erro
	 * @param error mensagem de erro
	 * @see ClientSideMessage.Type
	 * 
	 */

	@Override
	public void sendError(String toNickname, String error) {
		try {
			for (int i = 0; i < userList.size(); i++) {
				if(userList.get(i).getNick() == toNickname)
					userList.get(i).getOut().writeObject(new MessageFromServer(null, error, toNickname, moodishcomm.comm.ClientSideMessage.Type.ERROR));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// O que estava antes
		/*try {
			out.writeObject(new MessageFromServer(null, error, toNickname, moodish.comm.ClientSideMessage.Type.ERROR));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}
	

	/**
	 * @author luis
	 * Metodo que verifica se o cliente está conectado
	 * @param nickname nome do cliente que está a ser feita a verificacao
	 * @return <code>true</code> caso o cliente esteja conectado
	 */
	@Override
	public boolean clientIsConnected(String nickname) {
		for (int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getNick() == nickname)
				return true;
		}
		return false;
	}
	
	/**
	 * @author luis 
	 * Metodo que envia para um cliente a avisar que outro cliente se connectou
	 * @param toNickname nome do destino da mensagem
	 * @param userConnected nome de quem se connectou
	 * @see ClientSideMessage.Type
	 */

	@Override
	public void sendClientConnected(String toNickname, String userConnected) {
		try {
			for (int i = 0; i < userList.size(); i++) {
				if(userList.get(i).getNick() == toNickname)
					userList.get(i).getOut().writeObject(new MessageFromServer(null, userConnected, toNickname, moodishcomm.comm.ClientSideMessage.Type.CONNECTED));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// O que estava aqui antes
		/*try {
			out.writeObject(new MessageFromServer(null, userConnected, toNickname, moodish.comm.ClientSideMessage.Type.CONNECTED));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}

	/**
	 * @author luis
	 * Metodo que envia para um cliente a avisar que alguem se desconectou
	 * @param toNickname nome do destino da mensagem
	 * @param userDisconnected nome de quem se desconnectou
	 * @see ClientSideMessage.Type
	 */
	@Override
	public void sendClientDisconnected(String toNickname, String userDisconnected) {
		try {
			for (int i = 0; i < userList.size(); i++) {
				if(userList.get(i).getNick() == toNickname)
					userList.get(i).getOut().writeObject(new MessageFromServer(null, userDisconnected, toNickname, moodishcomm.comm.ClientSideMessage.Type.DISCONNECTED));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// O que estava aqui antes:
		/*try {
			out.writeObject(new MessageFromServer(null, userDisconnected, toNickname, moodish.comm.ClientSideMessage.Type.DISCONNECTED));
		} catch (IOException e) {
			e.printStackTrace();
		}*/		
	}

	/**
	 * @author marta
	 * Metodo que remove da lista de clientes, o cliente que se desligou
	 * @param nickname nome do cliente que se desligou
	 */
	@Override
	public void disconnectClient(String nickname) {
		for (int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getNick() == nickname) {
				// remove client from list
				userList.remove(i);
				// Group 10: stop thread
				listenClient = false;
			}
		}
		// Group 10: Send to server the disconnect message
		addMessage(new MessageFromClient(nickname, null, null, moodishcomm.comm.ServerSideMessage.Type.CLIENT_DISCONNECTED));
	}

	/**
	 * @author luis
	 * Metodo que envia uma mensagem para um cliente do tipo moddishMessage
	 * @param fromNicename nome do cliente que enviou a mensagem
	 * @param toNickname nome do cliente destino da mensagem
	 * @param message menssagem
	 * @see ClientSideMessage.Type
	 */
	@Override
	public void sendMoodishMessage(String fromNicename, String toNickname, String message) {
		try {
			for (int i = 0; i < userList.size(); i++) {
				if(userList.get(i).getNick() == toNickname)
					userList.get(i).getOut().writeObject(new MessageFromServer(fromNicename, message, toNickname, moodishcomm.comm.ClientSideMessage.Type.MOODISH_MESSAGE));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * @author joao
	 * Metodo que envia uma mensagem para um canal do tipo Unfriendship
	 * @param toNickname nome cliente destino da mensagem
	 * @param newFriendship nome do cliente que realizou a acao
	 * @see ClientSideMessage.Type
	 */
	@Override
	public void sendNewUnfriendship(String toNickname, String newFriendship) {
		try {
			for (int i = 0; i < userList.size(); i++) {
				if(userList.get(i).getNick() == toNickname)
					userList.get(i).getOut().writeObject(new MessageFromServer(newFriendship, null, toNickname, moodishcomm.comm.ClientSideMessage.Type.UNFRIENDSHIP));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Metodo que adiciona uma mensagem a fila de mensagens, está sincronizado para nao provocar problemas de leitura e escrita na filaDemensagens
	 * @param message mensagem a ser adicionada a fila
	 */	
	public synchronized void addMessage(MessageFromClient message){
		filaDeMensagens.add(message);
	}
	
	
	/**
	 * 
	 * @author joao
	 * @version 1.1
	 * @see Thread
	 */
	
	public class DealWithClient extends Thread {
		private Socket socket;
		private String nick;
		
		/**
		 * Constrotor da Thread que escuta os cliente
		 * @see Socket
		 * @param socket
		 */
		public DealWithClient(Socket socket) {
			this.socket = socket;
			try {
				in = new ObjectInputStream(socket.getInputStream());// possivelmente pode ser ao contrario
				out = new ObjectOutputStream(socket.getOutputStream());
				MessageFromClient mesCli = (MessageFromClient) in.readObject();
				// Group 10: add the connect message
				addMessage(mesCli);
				nick = mesCli.getClientNickname();
				userList.add(new ClientInformation(nick, out));	
			} catch (IOException ioEx) {
				ioEx.printStackTrace();
			} catch (ClassNotFoundException cnfEx) {
				cnfEx.printStackTrace();
			}
		}

		/**
		 * Metodo que ouve do canal e escreve na filaDeMensagens
		 * @see InputStream
		 */
		@Override
		public void run() {
			try {
				while(listenClient && !socket.isClosed()) {
					MessageFromClient message = (MessageFromClient) in.readObject();
					System.out.println(message);
					addMessage(message);
					// Group 10: Is disconnect message
					if(message.getType()==ServerSideMessage.Type.CLIENT_DISCONNECTED) 
						listenClient = false;
				}
			} catch(EOFException e) {
//				e.printStackTrace();
				// Group 10: peer has ended connection
				listenClient = false; 
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

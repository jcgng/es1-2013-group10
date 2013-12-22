package moodishclient.client.window;

import moodishclient.client.callbacks.StartScreenCallback;
import moodishclient.client.dataModels.UserData;
import moodishclient.client.states.Mood;
import moodishclient.client.states.UserSessionState;
import moodishclient.client.views.*;
import moodishclient.client.views.viewComponents.ConversationView;
import moodishclient.client.views.viewComponents.FriendInformation;
import moodishclient.client.windowInnerFixedComponents.ApplicationContent;
import moodishclient.client.windowInnerFixedComponents.MenuLogOut;
import moodishclient.client.windowInnerFixedComponents.ToolBarMenu;
import moodishcomm.comm.ClientComm;
import moodishcomm.comm.ClientSideMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartScreen extends JFrame implements StartScreenCallback {
	private static final long serialVersionUID = 1L;

	private static final String SERVER_URL  = "127.0.0.1";

    private static final LayoutManager GRID_BAG_LAYOUT_MANAGER = new GridBagLayout();
    private static final LayoutManager BORDER_LAYOUT_MANAGER = new BorderLayout();

    private static final String FRIENDS_KEY = "Friends";
    private static final String SEARCH_PEOPLE_KEY = "<html>Search<br>people</html>";
    private static final String SEND_MOOD_KEY = "<html>Send<br>Mood</html>";
    private static final String CHAT_KEY = "<html>Received<br>Moods</html>";
    private static final String INFORMATION_KEY = "<html>User<br>information</html>";

    private static final String FRIENDS_TIP_TEXT = "See your friends";
    private static final String SEARCH_PEOPLE_TIP_TEXT = "Search for people";
    private static final String SEND_MOOD_TIP_TEXT = "Share your mood with your friends";
    private static final String CHAT_TIP_TEXT = "Talk with your friends";
    private static final String INFORMATION_TIP_TEXT = "Check your profile";

    private Container container;

    private ApplicationContent applicationContent;

    private ChatView chatView;
    private SendMoodView sendMoodView;
    private FriendsView friendsView;
    private FindPeopleView findPeopleView;
    private ClientInfoView clientInfoView;

    private UserData userData;

    private JTabbedPane tabs;

    private ClientComm clientComm;
    
    private Thread messageListener;

    public StartScreen(ClientComm clientComm) {
        this.clientComm = clientComm;

        container = getContentPane();

        userData = new UserData();

        showLoginForm();
    }

    private ApplicationContent loadPrincipalScreens() {
        applicationContent = new ApplicationContent();

        chatView = new ChatView(userData, this);
        friendsView = new FriendsView();
        findPeopleView = new FindPeopleView(this);
        clientInfoView = new ClientInfoView(userData);
        sendMoodView = new SendMoodView(this);

        applicationContent.addScreen(friendsView, FRIENDS_KEY);
        applicationContent.addScreen(sendMoodView, SEND_MOOD_KEY);
        applicationContent.addScreen(findPeopleView, SEARCH_PEOPLE_KEY);
        applicationContent.addScreen(chatView, CHAT_KEY);
        applicationContent.addScreen(clientInfoView, INFORMATION_KEY);

        return applicationContent;
    }

    private MenuLogOut loadLogoutMenu() {
        return new MenuLogOut(userData.getNickname(), this);
    }

    private ToolBarMenu loadMenuToolbar(ActionListener menuToolBarListener) {

        ToolBarMenu toolBarMenu = new ToolBarMenu();

        toolBarMenu.addMenuButton(null, FRIENDS_KEY, FRIENDS_TIP_TEXT, FRIENDS_KEY, Color.BLUE).addActionListener(menuToolBarListener);
        toolBarMenu.addMenuButton(null, SEND_MOOD_KEY, SEND_MOOD_TIP_TEXT, SEND_MOOD_KEY, Color.RED).addActionListener(menuToolBarListener);
        toolBarMenu.addMenuButton(null, SEARCH_PEOPLE_KEY, SEARCH_PEOPLE_TIP_TEXT, SEARCH_PEOPLE_KEY, Color.CYAN).addActionListener(menuToolBarListener);
        toolBarMenu.addMenuButton(null, CHAT_KEY, CHAT_TIP_TEXT, CHAT_KEY, Color.GREEN).addActionListener(menuToolBarListener);
        toolBarMenu.addMenuButton(null, INFORMATION_KEY, INFORMATION_TIP_TEXT, INFORMATION_KEY, Color.MAGENTA).addActionListener(menuToolBarListener);

        return toolBarMenu;
    }

    private void enterApplication() {
        container.removeAll();
        container.setLayout(BORDER_LAYOUT_MANAGER);

        ActionListener menuToolBarListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applicationContent.changeScreen(e.getActionCommand());
            }
        };

        container.add(loadPrincipalScreens(), BorderLayout.CENTER);
        container.add(loadMenuToolbar(menuToolBarListener), BorderLayout.WEST);
        container.add(loadLogoutMenu(), BorderLayout.NORTH);
        container.revalidate();
        container.repaint();
        
        
       messageListener = new Thread(new Runnable() {
		
		@Override
		public void run() {
			System.out.println("Client waiting for messages: ");
            while(clientComm.isConnected()) {
            	if(clientComm.hasNextMessage()) {
	                ClientSideMessage message = clientComm.getNextMessage();
	                ClientSideMessage.Type messageType = message.getType();
	                
	                System.out.println("[Client] message received: " + messageType + " payload: " + message.getPayload());
	
	                if (messageType == ClientSideMessage.Type.CONNECTED) {
	                    findPeopleView.addOnlinePerson(message.getPayload());
	                } else if (messageType == ClientSideMessage.Type.DISCONNECTED) {
	                    findPeopleView.removeOnlinePerson(message.getPayload());
	                } else if (messageType == ClientSideMessage.Type.MOODISH_MESSAGE) {
	                    if (userData.isConversationOpened(message.getSendersNickname())) {
	                        ConversationView conversation = userData.getOpenConversation(message.getSendersNickname());
	                        conversation.receiveMessage(message.getPayload());
	                        chatView.notifyMessageFromFriend(conversation);
	                    } else {
	                        // Opens a new Conversation
	                        ConversationView newConversation = new ConversationView(userData, message.getSendersNickname());
	                        newConversation.receiveMessage(message.getPayload());
	                        chatView.addNewConversation(newConversation);
	                    }
	                    userData.getConnectedFriend(message.getSendersNickname()).setMood(message.getPayload());
	                    applicationContent.changeScreen(CHAT_KEY);
	                } else if (messageType == ClientSideMessage.Type.FRIENDSHIP) {
	                	applicationContent.changeScreen(FRIENDS_KEY);
	                	FriendInformation newFriend = new FriendInformation(message.getSendersNickname(), StartScreen.this);
	                	userData.addConnectedFriend(newFriend);
	                    friendsView.addFriend(newFriend);
	                    JOptionPane.showMessageDialog(StartScreen.this , "You receive a friendship request from " + message.getSendersNickname());
	                } else if (messageType == ClientSideMessage.Type.UNFRIENDSHIP) {
	                	friendsView.removeFriend(userData.getConnectedFriend(message.getPayload()));
	                    userData.removeConnectedFriend(message.getPayload());
	                    if(userData.isConversationOpened(message.getPayload())) {
	                    	chatView.removeConversation(userData.getOpenConversation(message.getPayload()));
	                    }
	                	JOptionPane.showMessageDialog(StartScreen.this , "You receive a unfriendship request from " + message.getSendersNickname());
	                } else if (messageType == ClientSideMessage.Type.ERROR) {
	                    showErrorMessage("An error occurred", message.getPayload());
	                    if(message.getPayload().equals("DISCONNECT")) exitToLogin();
	                }
            	}
            	// Group 10
    			try {
    				Thread.sleep(500);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
            }
            System.out.println("[Client] not waiting for messages anymore!");
		}
	});
       
       messageListener.start();
                   
    }

    private void showLoginForm() {
        container.removeAll();
        container.setLayout(GRID_BAG_LAYOUT_MANAGER);
        container.add(new LoginView(userData, this));
        container.revalidate();
        container.repaint();
    }

    @Override
    public void validateUserNickname() {
        try {
            clientComm.connect(SERVER_URL, userData.getNickname());
            userData.setSessionState(UserSessionState.CONNECTED);
            enterApplication();
            System.out.println("Logged as: " + userData.getNickname());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @Override
    public void showErrorMessage(String messageTitle, String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, messageTitle, JOptionPane.ERROR_MESSAGE);
    }

    private void exitToLogin() {
    	userData.clean();
        showLoginForm();
    }
    
    @Override
    public void logoutUser() {
    	// Group 10: send disconnect to server
    	clientComm.disconnect();
    	try {
			messageListener.join();
		} catch (InterruptedException e) {
			showErrorMessage("Internal error", "An error has occured, please contact administrator");
		}
    	exitToLogin();
    }

    @Override
    public void confirmConversationClose(ConversationView conversationView, String confirmMessage) {
        int choosedAction = JOptionPane.showConfirmDialog(this, confirmMessage);
        if(choosedAction == 0) {
            chatView.removeConversation(conversationView);
        }
    }

    @Override
    public void sendMessage(String message) {
    	if(!clientComm.isConnected()) 
    		exitToLogin();
    	else {
	        chatView.broadcastMessage(message);
	        userData.setLastMood(message);
	        clientInfoView.setMood(message);
	        clientComm.sendMoodishMessage(message);
    		System.out.println("[Client] Moodish message sended: " + message);
    	}
    }

    @Override
    public void sendFriendshipRequest(String userNickname) {
    	if(!clientComm.isConnected()) 
    		exitToLogin();
    	else {
    		clientComm.friendship(userNickname);
    		System.out.println("[Client] Friendship request sended to " + userNickname);
    	}
        
    }

	@Override
	public void sendUnfriendshipRequest(String userNickname) {
		clientComm.unfriendship(userNickname);
		FriendInformation friend = userData.getConnectedFriend(userNickname);
		userData.removeConnectedFriend(userNickname);
		friendsView.removeFriend(friend);
		
		System.out.println("[Client] Unfriendship request sended to " + userNickname);

	}
}


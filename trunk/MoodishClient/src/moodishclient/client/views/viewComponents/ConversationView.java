package moodishclient.client.views.viewComponents;

import moodishclient.client.dataModels.UserData;

import java.awt.*;

import javax.swing.*;

public class ConversationView extends JPanel {

    private enum LastMessageSender {
        ME, SHE, NONE;
    }

    private static final String CHAT_VIEW_TITLE = "Moods received from friends";

    private LastMessageSender lastMessageSender = LastMessageSender.NONE;

    private JTextArea TextChatTextArea;

    //	 private JEditorPane TextChatEditorPane;
    private JComboBox sendComboBox;
    private DefaultComboBoxModel dcm;

    private JButton SendButton;

    private UserData userData;
    private String friendNickname;


    public ConversationView(UserData userData, String friendNickname){
        setLayout(new BorderLayout());

        TextChatTextArea= new JTextArea();
        TextChatTextArea.setLineWrap(true);
        TextChatTextArea.setWrapStyleWord(true);
        TextChatTextArea.setName(CHAT_VIEW_TITLE);
        TextChatTextArea.setEnabled(false);
        TextChatTextArea.setPreferredSize(new Dimension(250, 300));
        add(TextChatTextArea,BorderLayout.CENTER);

        JPanel ChatTextSendPanel = new JPanel();
        ChatTextSendPanel.setLayout(new FlowLayout());

//		TextChatEditorPane.setPreferredSize(new Dimension(600, 20));

        add(ChatTextSendPanel, BorderLayout.SOUTH);

        this.userData = userData;
        this.friendNickname = friendNickname;
        userData.openConversation(friendNickname, this);

    }


    public void sendMessage(String message) {
        if(lastMessageSender != LastMessageSender.ME) {
            TextChatTextArea.append("\n");
            TextChatTextArea.append("You're feeling: \n");
        }
        TextChatTextArea.append(message + "\n");

        lastMessageSender = LastMessageSender.ME;
    }

    public void receiveMessage(String message) {
        if(lastMessageSender != LastMessageSender.SHE) {
            TextChatTextArea.append("\n");
            TextChatTextArea.append(friendNickname + " is feeling: \n");
        }
        TextChatTextArea.append(message + "\n");

        lastMessageSender = LastMessageSender.SHE;
    }

    public String getFriendNickname() {
        return friendNickname;
    }



}
	

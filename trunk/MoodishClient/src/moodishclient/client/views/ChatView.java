package moodishclient.client.views;

import moodishclient.client.callbacks.StartScreenCallback;
import moodishclient.client.dataModels.UserData;
import moodishclient.client.views.viewComponents.ConversationView;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;


public class ChatView extends JPanel {

    private static final int[] keys = {KeyEvent.VK_0,KeyEvent.VK_1,KeyEvent.VK_2,KeyEvent.VK_3,KeyEvent.VK_4,KeyEvent.VK_5,KeyEvent.VK_6,KeyEvent.VK_7,KeyEvent.VK_8,KeyEvent.VK_9};

    private Color defaultBackgroundColor;
    private Color defaultForegroundColor;

    private JTabbedPane tabs;

    private UserData userData;

    StartScreenCallback callback;

    public ChatView(UserData userData, StartScreenCallback callback){

        setLayout(new BorderLayout());

        tabs = new JTabbedPane();

        add(tabs);

        this.userData = userData;
        this.callback = callback;
    }

    public void addNewConversation(final ConversationView newConversation) {
        tabs.addTab(null, newConversation);
        int pos = tabs.indexOfComponent(newConversation);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.confirmConversationClose(newConversation, "Are you sure you want to close " + newConversation.getFriendNickname() + " conversation?");

            }
        };

        PanelTab pnlTab = new PanelTab(newConversation.getFriendNickname(), listener);

        tabs.setTabComponentAt(pos, pnlTab);

        tabs.setSelectedComponent(newConversation);

        if(pos < 10) {
            tabs.setMnemonicAt(pos, keys[pos]);
        }

        if(pos == 0) {
            defaultForegroundColor = pnlTab.getForeground();
            defaultBackgroundColor = pnlTab.getBackground();
        }

    }

    public void removeConversation(ConversationView conversationView) {
        tabs.remove(conversationView);
    }

    public void notifyMessageFromFriend(ConversationView conversation) {
        tabs.setSelectedComponent(conversation);
    }

    /*
    public void putTabToBlink(String friendNickname) {
        final int index = getTabIndexByFriendNickname(friendNickname);

        if(index == -1) {
            return;
        }

        tabs.setSelectedIndex(index);

        Timer timer = new Timer(500, new ActionListener() {
            boolean isBlinkTime = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if(isBlinkTime) {
                    tabs.setForegroundAt(index, Color.green);
                    tabs.setBackgroundAt(index, Color.orange);
                } else {
                    tabs.setForegroundAt(index, defaultForegroundColor);
                    tabs.setForegroundAt(index, defaultBackgroundColor);
                }
                isBlinkTime = !isBlinkTime;
                tabs.repaint();
            }
        });
        timer.start();
    } */


    public void broadcastMessage(String message) {

        Map<String, ConversationView> conversations = userData.getOpenConversations();
        for(ConversationView c : conversations.values()) {
            c.sendMessage(message);
        }
    }

    public class PanelTab extends JPanel {


        public PanelTab(String friendNickname, ActionListener actionListener) {
            setLayout(new GridBagLayout());

            setPreferredSize(new Dimension(100, 50));

            setOpaque(false);

            JLabel lblTitle = new JLabel(friendNickname);

            JButton btnClose = new JButton("X");

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy= 0;
            gbc.weightx = 1;

            add(lblTitle, gbc);

            gbc.gridx++;
            gbc.weightx = 0;
            add(btnClose, gbc);

            setToolTipText("Open current conversation with " + friendNickname);

            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

            btnClose.addActionListener(actionListener);

        }



    }

}

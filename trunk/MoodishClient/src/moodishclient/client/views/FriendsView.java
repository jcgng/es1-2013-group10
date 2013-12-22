package moodishclient.client.views;



import moodishclient.client.views.viewComponents.FriendInformation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FriendsView extends JPanel implements ActionListener {

    private final String SORT_BY_MOOD = "Sort by mood";
    private final String SORT_BY_NAME = "Sort by name";

    private JComboBox sortFilter;
    private DefaultComboBoxModel cbm;

    private List<FriendInformation> connectedFriends = new LinkedList<FriendInformation>();

    private GridBagConstraints gbc;

    private JPanel friendsPanel;

    public FriendsView() {
        setLayout(new BorderLayout());


        friendsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        friendsPanel.setPreferredSize(new Dimension(200, 600));

        sortFilter = new JComboBox();
        sortFilter.setPreferredSize(new Dimension(80, 25));
        sortFilter.addActionListener(this);
        cbm = new DefaultComboBoxModel();
        sortFilter.setModel(cbm);

        cbm.addElement(SORT_BY_MOOD);
        cbm.addElement(SORT_BY_NAME);

        add(sortFilter, BorderLayout.NORTH);

        add(friendsPanel, BorderLayout.CENTER);
    }


    public void addFriend(FriendInformation friend) {
        connectedFriends.add(friend);
        friendsPanel.add(friend);
        friendsPanel.revalidate();
        friendsPanel.repaint();

    }

    public void removeFriend(FriendInformation friend) {
        connectedFriends.remove(friend);
        friendsPanel.remove(friend);
        friendsPanel.revalidate();
        friendsPanel.repaint();
    }


    private void refreshFriendsView(Object[] array) {
        friendsPanel.removeAll();
        for(Object friend : array) {
            addFriend((FriendInformation)friend);
        }
        friendsPanel.revalidate();
        friendsPanel.repaint();

    }

    public void displayByMood() {
        Object[] array = connectedFriends.toArray();
        Arrays.sort(array, FriendInformation.friendsMoodComparator);
        refreshFriendsView(array);
    }

    public void displayByName() {
        Object[] array = connectedFriends.toArray();
        Arrays.sort(array, FriendInformation.friendsNicknameComparator);
        refreshFriendsView(array);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(sortFilter.getSelectedItem().equals(SORT_BY_MOOD)) {
            System.out.println("Sort by mood");
            displayByMood();
        } else {
            System.out.println("Sort by name");
            displayByName();
        }

    }


}

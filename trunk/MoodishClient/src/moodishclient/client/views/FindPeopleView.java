package moodishclient.client.views;

import moodishclient.client.callbacks.StartScreenCallback;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FindPeopleView extends JPanel implements ActionListener{

    private JList listOfPeople;

    private DefaultListModel dlm;

    private JButton btnNewButton;

    private StartScreenCallback callback;

    public FindPeopleView(StartScreenCallback callback) {
        setLayout(new BorderLayout());
        JPanel find = new JPanel();
        find.setLayout(new FlowLayout());

        dlm = new DefaultListModel();

        listOfPeople = new JList(dlm);

        listOfPeople.setPreferredSize(new Dimension(500, 700));

		/*Jpanel que contem o
		 * Jpanel da infopeople
		 * e o Butao para adiciondar amigo
		 * */
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;

        btnNewButton = new JButton("Send friendship request");
        btnNewButton.setPreferredSize(new Dimension(200, 50));
        btnNewButton.addActionListener(this);

        infoPanel.add(btnNewButton, gbc);

        add(find, BorderLayout.NORTH);
        add(listOfPeople, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);

        this.callback = callback;

    }

    public void addOnlinePerson(String personNickname) {
        dlm.addElement(personNickname);
    }

    public void removeOnlinePerson(String personNickname) {
        dlm.removeElement(personNickname);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!dlm.isEmpty()) {
            String userName = (String) dlm.getElementAt(listOfPeople.getSelectedIndex());
            if(userName != null) {
                callback.sendFriendshipRequest(userName);
            }
        }

    }

}


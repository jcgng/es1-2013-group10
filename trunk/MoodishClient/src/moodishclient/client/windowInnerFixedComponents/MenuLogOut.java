package moodishclient.client.windowInnerFixedComponents;

import moodishclient.client.callbacks.StartScreenCallback;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuLogOut extends JPanel implements ActionListener {
    private JButton logOutButton;
    private JLabel userName;

    private StartScreenCallback callback;

    public MenuLogOut(String user, StartScreenCallback callback) {
        userName = new JLabel("user: " + user);
        logOutButton = new JButton("LogOut");
        logOutButton.addActionListener(this);
        //setPreferredSize(new Dimension(100, 50));
        //setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setLayout(new FlowLayout());
        setAlignmentX(RIGHT_ALIGNMENT);
        add(userName);
        add(logOutButton);


        this.callback = callback;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        callback.logoutUser();
    }

}

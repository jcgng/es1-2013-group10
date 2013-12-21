package moodishclient.client.views;

import moodishclient.client.dataModels.UserData;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientInfoView extends JPanel {

    private JLabel NameClientLabel;

    private JLabel ClientMood;
    private String mood;

    public ClientInfoView(UserData userDate){

        setLayout(new GridBagLayout());
        setAlignmentY(LEFT_ALIGNMENT);
        NameClientLabel= new JLabel("User: " + userDate.getNickname());
        ClientMood = new JLabel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridy=0;
        gbc.gridx=0;

        add(NameClientLabel, gbc);
        gbc.gridy++;
        add(ClientMood, gbc);

    }

    public void setMood(String mood) {
        this.mood = mood;
        ClientMood.setText("Last mood: " + mood);

    }



}

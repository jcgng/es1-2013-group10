package moodishclient.client.views;

import moodishclient.client.callbacks.StartScreenCallback;
import moodishclient.client.states.Mood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SendMoodView extends JPanel implements ActionListener {

    private static final String SEND_BUTTON_TEXT = "SEND MOOD";
    private static final String LABEL_FIELD_DEFAULT_TEXT = "You're feeling: ";

    private JComboBox sendComboBox;
    private DefaultComboBoxModel dcm;
    private JButton sendButton;
    private JLabel moodTextLabel;

    StartScreenCallback callback;

    public SendMoodView(StartScreenCallback callback) {
        setLayout(new GridBagLayout());
        sendComboBox = new JComboBox();
        dcm = new DefaultComboBoxModel();
        sendComboBox.setModel(dcm);

        loadComboBoxValues();

        sendComboBox.setPreferredSize(new Dimension(250, 35));

        sendButton = new JButton(SEND_BUTTON_TEXT);
        sendButton.setSize(getMinimumSize());
        sendButton.addActionListener(this);

        moodTextLabel = new JLabel();
        moodTextLabel.setText(LABEL_FIELD_DEFAULT_TEXT);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(400, 200));
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(sendComboBox);
        panel.add(sendButton);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx=0;
        gbc.gridy++;
        panel.add(moodTextLabel, gbc);

        add(panel);

        sendComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                moodTextLabel.setText(LABEL_FIELD_DEFAULT_TEXT + " " + Mood.lookupValue((Mood) e.getItem()));
            }
        });

        this.callback = callback;

    }


    private void loadComboBoxValues() {
        for(Mood mood : Mood.values()) {
            dcm.addElement(mood);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selected = Mood.lookupValue((Mood) sendComboBox.getSelectedItem());
        if(selected != null) {
            callback.sendMessage(selected);
        }

    }

}

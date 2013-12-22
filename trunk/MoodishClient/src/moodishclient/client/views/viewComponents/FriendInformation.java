package moodishclient.client.views.viewComponents;

import moodishclient.client.callbacks.StartScreenCallback;
import moodishclient.client.states.Mood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;


public class FriendInformation extends JPanel implements ActionListener {

	
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    String mood = null;
    private String friendNickname;

    private JLabel MOOD_TITLE_LABEL = null;

    private JLabel moodLabel;
    
    private StartScreenCallback callback;

    public FriendInformation(String friendNickname, StartScreenCallback callback) {
        this.friendNickname = friendNickname;
        this.callback = callback;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JLabel nickLabel = new JLabel("<html><b>" + friendNickname + "</b></html>");
        nickLabel.setPreferredSize(new Dimension(WIDTH, ((int)(HEIGHT*0.45))));

        MOOD_TITLE_LABEL = new JLabel();
        MOOD_TITLE_LABEL.setPreferredSize(new Dimension(WIDTH, ((int)(HEIGHT*0.25))));

        moodLabel = new JLabel("");
        moodLabel.setPreferredSize(new Dimension(WIDTH, ((int)(WIDTH*0.35))));
        moodLabel.setForeground(Color.BLUE);
        
        JButton unfriendshipButton = new JButton();
        unfriendshipButton.setText("Unfriendship");
        unfriendshipButton.setPreferredSize(new Dimension((int)(WIDTH*0.50), 10));
        unfriendshipButton.setAlignmentX(CENTER_ALIGNMENT);
        
        unfriendshipButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx=0;
        gbc.gridy=0;
        add(nickLabel, gbc);
        gbc.gridy++;
        gbc.gridy++;
        add(MOOD_TITLE_LABEL, gbc);
        gbc.gridy++;
        add(moodLabel, gbc);
        gbc.gridy++;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(unfriendshipButton, gbc);
        

    }

    public String getFriendNickname() {
        return friendNickname;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
    	if(this.mood == null) {
    		MOOD_TITLE_LABEL.setText("is feeling");
    	}
        this.mood = mood;
        moodLabel.setText(mood);
        revalidate();
        repaint();
    }


    public static Comparator<Object> friendsMoodComparator = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            if(((FriendInformation)o1).getMood() != null && ((FriendInformation)o2).getMood() != null) {
                return ((FriendInformation)o1).getMood().toUpperCase().compareTo(((FriendInformation)o2).getMood().toUpperCase());
            }
            return 0;
        }
    };

    public static Comparator<Object> friendsNicknameComparator = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            return ((FriendInformation)o1).getFriendNickname().toUpperCase().compareTo(((FriendInformation)o2).getFriendNickname().toUpperCase());
        }
    };

	@Override
	public void actionPerformed(ActionEvent e) {
		callback.sendUnfriendshipRequest(friendNickname);
	}
}

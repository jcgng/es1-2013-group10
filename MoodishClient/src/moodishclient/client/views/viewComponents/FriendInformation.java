package moodishclient.client.views.viewComponents;

import moodishclient.client.states.Mood;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;


public class FriendInformation extends JPanel {

    private static JLabel MOOD_TITLE = null;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    Mood mood = null;
    private String friendNickname;

    private JLabel moodLabel;

    public FriendInformation(String friendNickname) {
        this.friendNickname = friendNickname;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JLabel nickLabel = new JLabel("<html><b>" + friendNickname + "</b></html>");
        nickLabel.setPreferredSize(new Dimension(WIDTH, ((int)(HEIGHT*0.45))));

        MOOD_TITLE = new JLabel("is feeling");
        MOOD_TITLE.setPreferredSize(new Dimension(WIDTH, ((int)(HEIGHT*0.25))));

        moodLabel = new JLabel();
        moodLabel.setPreferredSize(new Dimension(WIDTH, ((int)(WIDTH*0.35))));
        moodLabel.setForeground(Color.BLUE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx=0;
        gbc.gridy=0;
        add(nickLabel, gbc);
        gbc.gridy++;
        gbc.gridy++;
        add(MOOD_TITLE, gbc);
        gbc.gridy++;
        add(moodLabel, gbc);


    }

    public String getFriendNickname() {
        return friendNickname;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
        moodLabel.setText(Mood.lookupValue(mood));

    }


    public static Comparator<Object> friendsMoodComparator = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            if(((FriendInformation)o1).getMood() != null && ((FriendInformation)o2).getMood() != null) {
                return Mood.lookupValue(((FriendInformation)o1).getMood()).toUpperCase().compareTo(Mood.lookupValue(((FriendInformation)o2).getMood()).toUpperCase());
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
}

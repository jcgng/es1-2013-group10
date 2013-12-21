package moodishclient.client.windowInnerFixedComponents;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ApplicationContent extends JPanel implements ActionListener {

   // private List<JPanel> cards = new LinkedList<JPanel>();
    private CardLayout cardLayout = new CardLayout();

    public ApplicationContent() {
        setLayout(cardLayout);
        setPreferredSize(new Dimension(20, 20));

        setBackground(Color.WHITE);
    }

    public void addScreen(JPanel screen, String screenName) {
        add(screen, screenName);
    }

    public void changeScreen(String name) {
        cardLayout.show(this, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        changeScreen(((JButton)e.getSource()).getName());
    }
}

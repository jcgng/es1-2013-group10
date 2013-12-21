package moodishclient;

import moodishclient.client.MoodishClient;
import moodishclient.client.window.StartScreen;
import moodishcomm.comm.ClientComm;

import javax.swing.*;

public class MyMoodishClient implements MoodishClient {
    private StartScreen app;
    
	@Override
	public void start(final ClientComm clientComm) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InstantiationException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    StartScreen screen = new StartScreen(clientComm);
                    screen.setTitle("Moodish");
                    //screen.setBackground(Color.BLUE);

                    screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    screen.setLocationRelativeTo(null);
                    screen.setVisible(true);
                    screen.setExtendedState(screen.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                }
            });

    }

}

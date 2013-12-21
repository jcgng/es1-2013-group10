package moodishclient.client.views;

import moodishclient.client.callbacks.StartScreenCallback;
import moodishclient.client.dataModels.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * initial menu that asks the user authentication, 
 * to be able to connect to server
 * @author Jos� Costa
 * @author Luis Pires
 * @author Sonia Morais
 */

public class LoginView extends JPanel implements ActionListener {

    private static final String LOGIN_VIEW_TITLE = "Login";
    private static final String NICK_NAME_LABEL_TEXT = "Your Nickname";
    public static final String OK_BUTTON_TEXT = "Ok";
    private static final String CANCEL_BUTTON_TEXT = "Cancel";

    private static final int MINIMUM_NICKNAME_LENGTH = 3;

    private JLabel nicknameLabel;
    private JTextField nicknameTextField;
    private JButton okButton;
    private JButton cancelButton;

    private StartScreenCallback callback;

    private UserData userData;
    /**
     * creates the login window
     * @param user
     * @param callback
     */
    public LoginView(UserData user, StartScreenCallback callback) {
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        nicknameLabel = new JLabel(NICK_NAME_LABEL_TEXT);
        add(nicknameLabel);

        nicknameTextField = new JTextField(15);
        add(nicknameTextField);

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        okButton = new JButton(OK_BUTTON_TEXT);
        okButton.addActionListener(this);
        buttonsPanel.add(okButton);

        cancelButton = new JButton(CANCEL_BUTTON_TEXT);
        cancelButton.addActionListener(this);
        buttonsPanel.add(cancelButton);

        add(buttonsPanel);

        userData = user;
        this.callback = callback;

    }

   
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 100);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ACTION ");
        if(e.getSource().equals(okButton)) {
            String nickName = nicknameTextField.getText();

            if(nickName == null) {
                callback.showErrorMessage("Invalid Nickame", "You should define your nickname");
            } else if(nickName.length() < MINIMUM_NICKNAME_LENGTH) {
                callback.showErrorMessage("Invalid Nickname", "Your nickname should have 3 or more characteres");
            } else {
                userData.setNickname(nicknameTextField.getText());
                callback.validateUserNickname();
            }

        } else {

             //Do nothing or return to a great screen like an image or other
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Login view destroyed!");
    }
}

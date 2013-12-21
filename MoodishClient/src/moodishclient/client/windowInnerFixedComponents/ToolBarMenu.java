package moodishclient.client.windowInnerFixedComponents;

import moodishclient.client.views.viewComponents.MenuButton;

import javax.swing.*;
import java.awt.*;

public class ToolBarMenu extends JPanel {



    private GridBagConstraints gbc;

    public ToolBarMenu() {
        super(new GridBagLayout());

        gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(30, 0, 30, 0);


        setPreferredSize(new Dimension(100, getHeight()));
    }

    public MenuButton addMenuButton (String imageName, String actionCommand, String toolTipText, String text, Color color) {


        gbc.gridy++;

        MenuButton menuButton = new MenuButton(null, text, toolTipText, color);
        menuButton.setActionCommand(actionCommand);

        add(menuButton, gbc);
        return menuButton;
    }




}

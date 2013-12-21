package moodishclient.client.views.viewComponents;

import java.awt.*;
import java.net.URL;

import javax.swing.*;
/**
 * defines the structure of the main menu buttons
 * 
 * @author Jos� Costa
 * @author Luis Pires
 * @author Sonia Morais
 * 
 */
public class MenuButton extends JButton {
	
	/**
	 * defines the structure of the button, the image, text and color to
	 * @param image_url
	 * @param text
	 * @param toolTipText
	 * @param color
	 */
	public MenuButton(String image_url, String text, String toolTipText, Color color){

        setOpaque(false);
        setName(text);
		setBackground(color);

        if(image_url != null) {
            URL imageURL = MenuButton.class.getResource(image_url);
            setIcon(new ImageIcon(imageURL, text));
        } else {
            setText(text);
            System.err.println("Resource not found: " + image_url);
        }

        setToolTipText(toolTipText);
		setBorder(BorderFactory.createLoweredSoftBevelBorder());
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setPreferredSize(new Dimension(75, 75));
        setMinimumSize(new Dimension(75, 75));
	}
	
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        super.paint(g2);
        g2.dispose();

    }
}
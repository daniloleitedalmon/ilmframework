package ilm.framework.assignment.modules;

import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class AssignmentModuleToolbar extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	public AssignmentModuleToolbar() {
		// TODO somehow define a height limit of 25 pixels
	}

	protected JButton makeButton(String imageName,
						         String actionCommand,
						         String toolTipText,
						         String altText) {
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		/* TODO image load and association to button
		String imgLocation = "images/" + imageName + ".gif";
		URL imageURL = ToolBarDemo.class.getResource(imgLocation);
		if (imageURL != null) {                      //image found
			button.setIcon(new ImageIcon(imageURL, altText));
		} else {                                     //no image found
			button.setText(altText);
			System.err.println("Resource not found: " + imgLocation);
		}
		*/
		return button;
	}

}

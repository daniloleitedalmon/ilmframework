package ilm.framework.modules;

import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class IlmModuleToolbar extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	public IlmModuleToolbar() {
		// TODO somehow define a height limit of 25 pixels
	}

	protected JButton makeButton(String imageName,
						         String actionCommand,
						         String toolTipText,
						         String altText) {
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.setIcon(new ImageIcon("resources/" + imageName + ".png", altText));
		return button;
	}

}

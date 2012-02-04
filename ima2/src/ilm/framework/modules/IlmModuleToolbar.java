package ilm.framework.modules;

import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public abstract class IlmModuleToolbar extends JToolBar implements Observer {

	private static final long serialVersionUID = 1L;

	public IlmModuleToolbar() {
		setRollover(true);
		setFloatable(false);
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

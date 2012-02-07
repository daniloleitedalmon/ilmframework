package ilm.framework.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

import ilm.framework.SystemFactory;
import ilm.framework.assignment.IAssignment;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainGUI;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class BaseGUI extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected SystemConfig _config;
	protected SystemFactory _factory;
	protected ArrayList<DomainGUI> _domainGUIList;
	protected ArrayList<AuthoringGUI> _authoringGUIList;
	protected IAssignment _assignments;
	protected int _activeAssignment;
	
	public void setComponents(SystemConfig config, IAssignment commands, SystemFactory factory) {
		_config = config;
		_config.addObserver(this);
		_factory = factory;
		_domainGUIList = new ArrayList<DomainGUI>();
		_authoringGUIList = new ArrayList<AuthoringGUI>();
		_assignments = commands;
		_activeAssignment = 0;
	}

	public void initGUI() {
		initAssignments();
		initToolbar(_assignments.getIlmModuleList().values());
	}
	
	protected abstract void initAssignments();
	
	protected abstract void initToolbar(Collection<IlmModule> moduleList);

	
	public void startDesktop() {
		JFrame frame = new JFrame();
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 480);
		frame.setVisible(true);
	}
	
	
	protected void updateAssignmentIndex(int index) {
		_activeAssignment = index;
		for(IlmModule module: _assignments.getIlmModuleList().values()) {
			module.setAssignmentIndex(index);
			if(module instanceof AssignmentModule) {
				AssignmentModule m = (AssignmentModule)module;
				if(m.getObserverType() != AssignmentModule.ACTION_OBSERVER) {
					m.update(_assignments.getCurrentState(index), null);
				}
			}
		}
	}
	
	protected abstract void setAuthoringButton();
	
	protected abstract void setNewAssignmentButton();
	
	protected abstract void setCloseAssignmentButton();
	
	protected abstract void setOpenAssignmentButton();
	
	protected abstract void setSaveAssignmentButton();
	
	
	protected abstract void startAuthoring();
	
	protected abstract void addNewAssignment();
	
	protected abstract void closeAssignment(int index);
	
	protected abstract void openAssignmentFile(String fileName);
	
	protected abstract void saveAssignmentFile(String fileName);

	
	protected JButton makeButton(String imageName, String actionCommand, String toolTipText, String altText) {
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.setIcon(new ImageIcon("resources/" + imageName + ".png", altText));
		return button;
	}

}

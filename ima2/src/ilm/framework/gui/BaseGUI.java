package ilm.framework.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainGUI;
import ilm.framework.modules.IlmModule;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class BaseGUI extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected SystemConfig _config;
	protected ArrayList<DomainGUI> _domainGUIList;
	protected ArrayList<AuthoringGUI> _authoringGUIList;
	protected IAssignment _assignments;
	protected int _activeAssignment;
	
	public void setComponents(SystemConfig config, IAssignment commands, DomainGUI domainGUI) {
		_config = config;
		_config.addObserver(this);
		_assignments = commands;
		_activeAssignment = 0;
		_domainGUIList = new ArrayList<DomainGUI>();
		_domainGUIList.add(domainGUI);
		_authoringGUIList = new ArrayList<AuthoringGUI>();
	}

	public void initGUI() {
		initAssignments();
		initIlmModules(_assignments.getIlmModuleList().values());
	}

	// trazer esse método para cá
	// deixar o método initAssignment(state) para o IlmBaseGUI
	// enquanto que aqui fica o gerenciamento das domainGUI e das authoringGUI
	protected abstract void initAssignments();
	
	protected abstract void initAssignment(AssignmentState state);
	
	protected abstract void initIlmModules(Collection<IlmModule> moduleList);

	
	public void startDesktop() {
		JFrame frame = new JFrame();
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 480);
		frame.setVisible(true);
	}
	
	protected void setActiveAssignment(int index) {
		_activeAssignment = index;
		for(IlmModule module: _assignments.getIlmModuleList().values()) {
			module.setAssignmentIndex(index);
		}
	}
	
	protected abstract void setNewAssignmentButton();
	
	protected void addNewAssignment(AssignmentState currentState) {
		_domainGUIList.add((DomainGUI)_domainGUIList.get(0).clone());
		_domainGUIList.get(_domainGUIList.size()-1).setAssignment(currentState, _assignments.getIlmModuleList().values());
		_authoringGUIList.add(getAuthoringGUI());
		_assignments.newAssignment();
	}
	
	protected abstract void setCloseAssignmentButton();
	
	protected void closeAssignment(int index) {
		_domainGUIList.remove(index);
		_authoringGUIList.remove(index);
		_assignments.closeAssignment(index);
	}
	
	protected abstract void setOpenAssignmentButton();
	
	protected void openAssignmentFile(String fileName) {
		ArrayList<AssignmentState> assignmentList = _assignments.openAssignmentFile(fileName);
		for(AssignmentState state : assignmentList) {
			addNewAssignment(state);
			initAssignment(state);
		}
	}

	protected abstract void setAuthoringButton();
	
	protected void startAuthoring() {
		if(_authoringGUIList.get(_activeAssignment) == null) {
			_authoringGUIList.add(getAuthoringGUI());
		}
		_authoringGUIList.get(_activeAssignment).setDomainGUI(_domainGUIList.get(_activeAssignment));
		_authoringGUIList.get(_activeAssignment).setVisible(true);
	}
	
	protected abstract AuthoringGUI getAuthoringGUI();
	
	protected JButton makeButton(String imageName, String actionCommand, String toolTipText, String altText) {
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.setIcon(new ImageIcon("resources/" + imageName + ".png", altText));
		return button;
	}

}

package ilm.framework.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

import ilm.framework.assignment.IAssignment;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainGUI;
import ilm.framework.modules.IlmModule;

import javax.swing.JPanel;

public abstract class BaseGUI extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	protected SystemConfig _config;
	private ArrayList<DomainGUI> _domainGUIList;
	private ArrayList<AuthoringGUI> _authoringGUIList;
	private IAssignment _assignments;
	protected int _activeAssignment;

	public BaseGUI(SystemConfig config, IAssignment commands, DomainGUI domainGUI) {
		_config = config;
		_config.addObserver(this);
		_assignments = commands;
		_domainGUIList = new ArrayList<DomainGUI>();
		_domainGUIList.add(domainGUI);
		_authoringGUIList = new ArrayList<AuthoringGUI>();
		_authoringGUIList.add(getAuthoringGUI());
	}

	public void initGUI() {
		initAssignments();
		initIlmModules(_assignments.getIlmModuleList().values());
	}

	private void initAssignments() {
		// TODO Auto-generated method stub
		// if there is only one assignment in _config
		// 		set tab panel to disabled
		//		put on its place the only domainGUI
		//		set the index
		// else
		//		for each assignment in _config
		//			clone domainGUI
		//			set an index
		//			set the assignment (from _assignments) to domainGUI
		// 		check for active assignment in _config
		// 		set its tab active
	}
	
	protected abstract void initIlmModules(Collection<IlmModule> moduleList);

	
	public void startDesktop() {
		// TODO Auto-generated method stub
		// create a JForm
		// add this to its contentPane
		// set it visible
	}
	
	protected void setActiveAssignment(int index) {
		_activeAssignment = index;
		for(IlmModule module: _assignments.getIlmModuleList().values()) {
			module.setAssignmentIndex(index);
		}
		// TODO Auto-generated method stub
		// change active tab in tab panel
	}
	
	
	protected abstract void setAuthoringButton();
	
	protected void startAuthoring() {
		_authoringGUIList.get(_activeAssignment).setDomainGUI(_domainGUIList.get(_activeAssignment));
		_authoringGUIList.get(_activeAssignment).setAssignmentCommands(_assignments);
		_authoringGUIList.get(_activeAssignment).setVisible(true);
	}
	
	protected abstract AuthoringGUI getAuthoringGUI();
	
}

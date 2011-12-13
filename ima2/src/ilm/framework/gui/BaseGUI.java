package ilm.framework.gui;

import java.util.ArrayList;
import java.util.Observer;

import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.IModulesLists;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainGUI;
import ilm.framework.modules.IlmModule;

import javax.swing.JPanel;

public abstract class BaseGUI extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	protected SystemConfig _config;
	private IAssignment _assignments;
	protected IModulesLists _modules;
	private ArrayList<DomainGUI> _domainGUIList;
	protected int _activeDomainGUI;

	public BaseGUI(SystemConfig config, DomainGUI domainGUI) {
		_config = config;
		_config.addObserver(this);
		initAssignments(domainGUI);
		initGUI();
	}

	public void setAssignmentCommands(IAssignment assignments, IModulesLists modules) {
		_assignments = assignments;
		_modules = modules;
	}

	private void initAssignments(DomainGUI domainGUI) {
		_domainGUIList = new ArrayList<DomainGUI>();
		// TODO Auto-generated method stub
		// for each assignment in _config
		//		clone domainGUI
		//		set an index
		//		set the assignment (from _assignments) to domainGUI
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		// for each IlmModule in _modules
		//		define a menu or button
		// for each domainGUI
		//		for each AssignmentModule in _modules
		//			define a menu or button
	}

	public void startDesktop() {
		// TODO Auto-generated method stub
		// check for active assignment in _config
		// set its tab active
		// create a JForm
		// set visible
	}
	
	public abstract void setIlmModulesGUI(ArrayList<IlmModule> moduleList);
	
	protected void setActiveAssignment(int index) {
		for(IlmModule module: _modules.getIlmModuleList().values()) {
			module.setAssignmentIndex(index);
		}
	}
}

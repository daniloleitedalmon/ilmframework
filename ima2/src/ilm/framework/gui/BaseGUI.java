package ilm.framework.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

import ilm.framework.assignment.IAssignmentList;
import ilm.framework.assignment.IModulesLists;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainGUI;
import ilm.framework.modules.IlmModule;

import javax.swing.JPanel;

public abstract class BaseGUI extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	protected SystemConfig _config;
	private ArrayList<DomainGUI> _domainGUIList;
	private IAssignmentList _assignments;
	private IModulesLists _modules;
	protected int _activeDomainGUI;

	public BaseGUI(SystemConfig config, DomainGUI domainGUI) {
		_config = config;
		_config.addObserver(this);
		_domainGUIList = new ArrayList<DomainGUI>();
		_domainGUIList.add(domainGUI);
	}

	public void setAssignmentCommands(IAssignmentList assignments, IModulesLists modules) {
		_assignments = assignments;
		_modules = modules;
	}

	public void initGUI() {
		initAssignments();
		initAssignmentModules();
		initIlmModules(_modules.getIlmModuleList().values());
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
	
	private void initAssignmentModules() {
		// TODO Auto-generated method stub
		// for each domainGUI (each assignment)
		//		for each AssignmentModule in _modules
		//			define a menu or button
	}
	
	protected abstract void initIlmModules(Collection<IlmModule> moduleList);

	public void startDesktop() {
		// TODO Auto-generated method stub
		// create a JForm
		// set visible
	}
	
	protected void setActiveAssignment(int index) {
		for(IlmModule module: _modules.getIlmModuleList().values()) {
			module.setAssignmentIndex(index);
		}
		// TODO Auto-generated method stub
		// change active tab in tab panel
	}
	
}

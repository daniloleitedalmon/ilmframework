package ilm.framework.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.IModulesLists;
import ilm.framework.assignment.modules.AssignmentModule;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainGUI;
import ilm.framework.modules.IlmModule;

import javax.swing.JPanel;

public abstract class BaseGUI extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	protected SystemConfig _config;
	private ArrayList<DomainGUI> _domainGUIList;
	private IAssignment _assignments;
	private IModulesLists _modules;
	protected int _activeDomainGUI;

	public BaseGUI(SystemConfig config, DomainGUI domainGUI) {
		_config = config;
		_config.addObserver(this);
		_domainGUIList = new ArrayList<DomainGUI>();
		_domainGUIList.add(domainGUI);
	}

	public void setAssignmentCommands(IAssignment assignments, IModulesLists modules) {
		_assignments = assignments;
		_modules = modules;
	}

	public void initGUI() {
		initAssignments();
		for(int i = 0; i < _domainGUIList.size(); i++) {
			initAssignmentModules(_modules.getAssignmentModuleList(i).values());
		}
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
	
	protected abstract void initAssignmentModules(Collection<AssignmentModule> moduleList);
	
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
	
	
	protected abstract void setAuthoringButton();
	
	protected void startAuthoring() {
		AuthoringGUI authoring = getAuthoringGUI();
		authoring.setDomainGUI(_domainGUIList.get(_activeDomainGUI));
		authoring.setModules(_modules.getAssignmentModuleList(_activeDomainGUI));
		authoring.setAssignmentCommands(_assignments);
		authoring.setVisible(true);
	}
	
	protected abstract AuthoringGUI getAuthoringGUI();
	
}

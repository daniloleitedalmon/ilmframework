package ilm.framework;

import ilm.framework.assignment.AssignmentControl;
import ilm.framework.comm.CommControl;
import ilm.framework.config.SystemConfig;
import ilm.framework.gui.BaseGUI;
import ilm.framework.modules.AutomaticCheckingModule;

public final class SystemControl {

	private SystemFactory _factory;
	private SystemConfig _config;
	private AssignmentControl _assignmentControl;
	private CommControl _comm;
	private BaseGUI _gui;
	
	public void initialize(boolean isApplet, String[] parameterList, SystemFactory factory) {
		_config = new SystemConfig(isApplet, parameterList);
		_factory = factory;
		initComponents();
	}

	private void initComponents() {
		_comm = _factory.createCommControl(_config);
		_assignmentControl = _factory.createAssignmentControl(_config, _factory.createDomainConverter());
		_gui = _factory.createBaseGUI(_config, _factory.createDomainGUI(_factory.createDomainModel()));
		initComponentsCommunication();
	}
	
	private void initComponentsCommunication() {
		_assignmentControl.setCommProtocol(_comm);
		_gui.setAssignmentCommands(_assignmentControl, _assignmentControl);
	}
	
	public IlmProtocol getProtocol() {
		AutomaticCheckingModule module = 
				(AutomaticCheckingModule)_assignmentControl.getIlmModuleList().get("automatic_checking");
		module.setModel(_factory.createDomainModel());
		return module;
	}
	
	public void startDesktopGUI() {
		_gui.startDesktop();
	}
	
	public BaseGUI getAppletGUI() {
		return _gui;
	}

}

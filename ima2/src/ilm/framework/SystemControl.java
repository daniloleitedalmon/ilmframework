package ilm.framework;

import java.util.Map;

import ilm.framework.assignment.AssignmentControl;
import ilm.framework.comm.CommControl;
import ilm.framework.config.*;
import ilm.framework.gui.BaseGUI;

public final class SystemControl {

	private SystemConfig _config;
	private AssignmentControl _assignmentControl;
	private CommControl _comm;
	private BaseGUI _gui;
	
	public void initialize(boolean isApplet, String[] parameterList, SystemFactory factory) {
		IParameterListParser parser;
		if (isApplet) {
			parser = new AppletParameterListParser();
		}
		else {
			parser = new DesktopParameterListParser();
		}
		Map<String,String> parsedParameterList = parser.Parse(parameterList);
		_config = new SystemConfig(isApplet, parsedParameterList);
		initComponents(factory);
	}

	private void initComponents(SystemFactory factory) {
		_comm = factory.createCommControl(_config);
		_assignmentControl = factory.createAssignmentControl(_config, _comm, 
															 factory.getDomainModel(_config),
															 factory.getDomainConverter());
		_gui = factory.createBaseGUI(_config, _assignmentControl, factory);
	}
	
	public IlmProtocol getProtocol() {
		return _assignmentControl;
	}
	
	public void startDesktopGUI() {
		_gui.initGUI();
		_gui.startDesktop();
	}
	
	public BaseGUI getAppletGUI() {
		_gui.initGUI();
		return _gui;
	}

}

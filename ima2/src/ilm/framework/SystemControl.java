package ilm.framework;

import java.util.Map;

import ilm.framework.assignment.AssignmentControl;
import ilm.framework.comm.CommControl;
import ilm.framework.config.*;
import ilm.framework.domain.DomainModel;
import ilm.framework.gui.BaseGUI;
import ilm.framework.modules.AutomaticCheckingModule;

public final class SystemControl {

	private SystemFactory _factory;
	private SystemConfig _config;
	private AssignmentControl _assignmentControl;
	private CommControl _comm;
	private DomainModel _model;
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
		_factory = factory;
		initComponents();
	}

	private void initComponents() {
		_comm = _factory.createCommControl(_config);
		_model = _factory.createDomainModel(_config);
		_assignmentControl = _factory.createAssignmentControl(_config, _comm, _model, _factory.createDomainConverter());
		_gui = _factory.createBaseGUI(_config, _assignmentControl, _factory.createDomainGUI(_config, _model));
	}
	
	public IlmProtocol getProtocol() {
		return (AutomaticCheckingModule)_assignmentControl.getIlmModuleList().get(IlmProtocol.AUTO_CHECKING_MODULE_NAME);
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

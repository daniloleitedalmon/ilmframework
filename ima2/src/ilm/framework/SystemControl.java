package ilm.framework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

import ilm.framework.assignment.AssignmentControl;
import ilm.framework.comm.CommControl;
import ilm.framework.config.SystemConfig;
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
		Map<String,String> parsedParameterList = new HashMap<String, String>();
		// TODO Criar o parser de parametros apos definir com o Danilo
		
		// Decidir se aqui as excecoes serao tratadas ou repassadas.
		try {
			_config = new SystemConfig(isApplet, parsedParameterList);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		_factory = factory;
		initComponents();
	}

	private void initComponents() {
		_comm = _factory.createCommControl(_config);
		_model = _factory.createDomainModel(_config);
		_assignmentControl = _factory.createAssignmentControl(_config, _model);
		_gui = _factory.createBaseGUI(_config, _factory.createDomainGUI(_config, _model));
		initComponentsCommunication();
	}
	
	private void initComponentsCommunication() {
		_assignmentControl.setCommProtocol(_comm);
		_gui.setAssignmentCommands(_assignmentControl, _assignmentControl);
	}
	
	public IlmProtocol getProtocol() {
		AutomaticCheckingModule module = (AutomaticCheckingModule)
									_assignmentControl.getIlmModuleList().get("automatic_checking");
		module.setModel(_model);
		return module;
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

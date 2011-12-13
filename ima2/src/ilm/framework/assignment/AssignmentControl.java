package ilm.framework.assignment;

import java.util.ArrayList;
import java.util.HashMap;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.modules.AssignmentModule;
import ilm.framework.assignment.modules.HistoryModule;
import ilm.framework.assignment.modules.ObjectListModule;
import ilm.framework.assignment.modules.UndoRedoModule;
import ilm.framework.comm.ICommunication;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.IDomainConverter;
import ilm.framework.modules.AutomaticCheckingModule;
import ilm.framework.modules.IlmModule;

public final class AssignmentControl implements IAssignment, IAssignmentOperator, IModulesLists {

	private SystemConfig _config;
	private IDomainConverter _converter;
	private ICommunication _comm;
	private ArrayList<Assignment> _assignmentList;
	private HashMap<String, AssignmentModule> _availableAssignmentModules;
	private HashMap<String, IlmModule> _ilmModuleList;
	
	public AssignmentControl(SystemConfig config, IDomainConverter converter) {
		_config = config;
		_converter = converter;
		initAssignmentModuleList();
		initIlmModuleList();
		initAssignments();
	}

	private void initIlmModuleList() {
		_ilmModuleList = new HashMap<String, IlmModule>();
		IlmModule module = new AutomaticCheckingModule(this, this);
		_ilmModuleList.put(module.getName(), module);
	}

	private void initAssignmentModuleList() {
		_availableAssignmentModules = new HashMap<String, AssignmentModule>();
		AssignmentModule module = new UndoRedoModule();
		_availableAssignmentModules.put(module.getName(), module);
		module = new HistoryModule();
		_availableAssignmentModules.put(module.getName(), module);
		module = new ObjectListModule();
		_availableAssignmentModules.put(module.getName(), module);
	}

	public void setCommProtocol(ICommunication commProtocol) {
		_comm = commProtocol;
	}

	public void addAssignmentModule(AssignmentModule module) {
		_availableAssignmentModules.put(module.getName(), module);
	}
	
	public void addIlmModule(IlmModule module) {
		_ilmModuleList.put(module.getName(), module);
	}

	private void initAssignments() {
		// TODO Auto-generated method stub
		// for each assignment data in _config
		// 		load the corresponding file
		//		create the assignment
		//		add the respective modules to each one (modules are observers of actions in DomainGUI)
	}
	
	@Override
	public HashMap<String, AssignmentModule> getAssignmentModuleList(int index) {
		return _assignmentList.get(index).getModuleList();
	}
	
	@Override
	public HashMap<String, IlmModule> getIlmModuleList() {
		return _ilmModuleList;
	}

	@Override
	public AssignmentState getCurrentState(int index) {
		return _assignmentList.get(index).getCurrentState();
	}

	@Override
	public AssignmentState getInitialState(int index) {
		return _assignmentList.get(index).getInitialState();
	}

	@Override
	public AssignmentState getExpectedAnswer(int index) {
		return _assignmentList.get(index).getExpectedAnswer();
	}

	@Override
	public IDomainConverter getConverter() {
		return _converter;
	}

	@Override
	public ICommunication getFileRW() {
		return _comm;
	}

}

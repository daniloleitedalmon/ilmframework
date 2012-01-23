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
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AutomaticCheckingModule;
import ilm.framework.modules.IlmModule;

public final class AssignmentControl implements IAssignment, IAssignmentOperator, IModulesLists {

	private SystemConfig _config;
	private DomainModel _model;
	private ICommunication _comm;
	private ArrayList<Assignment> _assignmentList;
	private HashMap<String, AssignmentModule> _availableAssignmentModules;
	private HashMap<String, IlmModule> _ilmModuleList;
	
	public AssignmentControl(SystemConfig config, DomainModel model) {
		_config = config;
		_model = model;
		initAssignmentModuleList();
		initIlmModuleList();
		initAssignments();
	}

	
	private void initIlmModuleList() {
		_ilmModuleList = new HashMap<String, IlmModule>();
		IlmModule module = new AutomaticCheckingModule(this, this);
		_ilmModuleList.put(module.getName(), module);
	}
	
	public void addIlmModule(IlmModule module) {
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

	public void addAssignmentModule(AssignmentModule module) {
		_availableAssignmentModules.put(module.getName(), module);
	}

	
	private void initAssignments() {
		int numberOfAssignments;
		try {
			numberOfAssignments = Integer.parseInt(_config.getValue("numberOfAssignments"));
		}
		catch(NumberFormatException e) { 
			numberOfAssignments = 0;
			// TODO warn the user that the number of assignment was wrong
		}
		
		if(numberOfAssignments > 0) {
			for(int i = 0; i < numberOfAssignments; i++) {
				_assignmentList.add(createAssignment(loadAssignmentString(i)));
			}
		}
		else {
			_assignmentList.add(createNewAssignment());
		}
	}

	private Assignment createNewAssignment() {
		AssignmentState initialState = _model.getNewAssignmentState();
		return new Assignment("", initialState, initialState, null);
	}

	private Assignment createAssignment(String assignmentString) {
		AssignmentParser parser = new AssignmentParser();

		String proposition = parser.getProposition(assignmentString);
		AssignmentState initialState = parser.getInitialState(_model, assignmentString);
		AssignmentState currentState = parser.getCurrentState(_model, assignmentString);
		AssignmentState expectedState = parser.getExpectedAnswer(_model, assignmentString);
		ArrayList<AssignmentModule> moduleList = parser.getModuleList(_model, assignmentString);
		
		Assignment assignment = new Assignment(proposition, initialState, currentState, expectedState);
		for(AssignmentModule m : moduleList) {
			assignment.addModule(m);
		}
		return assignment;
	}
	
	private String loadAssignmentString(int assignmentIndex) {
		// TODO checks if _config has the assignment data for the index received as string or the file path
		//  loads the corresponding file
		//  converts it to string
		return "";
	}
	

	public void setCommProtocol(ICommunication commProtocol) {
		_comm = commProtocol;
	}


	/**
	 * @see IModuleLists
	 */
	@Override
	public HashMap<String, AssignmentModule> getAssignmentModuleList(int index) {
		return _assignmentList.get(index).getModuleList();
	}
	
	/**
	 * @see IModuleLists
	 */
	@Override
	public HashMap<String, IlmModule> getIlmModuleList() {
		return _ilmModuleList;
	}

	/**
	 * @see IAssignment
	 */
	@Override
	public AssignmentState getCurrentState(int index) {
		return _assignmentList.get(index).getCurrentState();
	}

	/**
	 * @see IAssignment
	 */
	@Override
	public AssignmentState getInitialState(int index) {
		return _assignmentList.get(index).getInitialState();
	}

	/**
	 * @see IAssignment
	 */
	@Override
	public AssignmentState getExpectedAnswer(int index) {
		return _assignmentList.get(index).getExpectedAnswer();
	}
	
	/**
	 * @see IAssignment
	 * 
	 * @return the index of newly created assignment
	 * 		requested by AuthoringGUI in BaseGUI
	 */
	@Override
	public int authorAssignment(Assignment assignment) {
		// TODO Auto-generated method stub
		// set in _config a new parameter regarding the authored assignment
		return 0;
	}

	/**
	 * @see IAssignmentOperator
	 * 
	 * @return the converter of file content to domain objects and actions
	 * 		requested by the iLM Modules
	 */
	@Override
	public DomainConverter getConverter() {
		return _model;
	}

	/**
	 * @see IAssignmentOperator
	 * 
	 * @return the reader and writer of files
	 * 		requested by the iLM Modules
	 */
	@Override
	public ICommunication getFileRW() {
		return _comm;
	}

}

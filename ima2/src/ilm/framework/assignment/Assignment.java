package ilm.framework.assignment;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.modules.AssignmentModule;

import java.util.HashMap;

public abstract class Assignment {

	private AssignmentState _initialState;
	private AssignmentState _currentState;
	private AssignmentState _expectedAnswer;
	private HashMap<String, AssignmentModule> _moduleList;
	
	public Assignment(AssignmentState initial) {
		_initialState = initial;
		_currentState = initial;
		_expectedAnswer = null;
		_moduleList = new HashMap<String, AssignmentModule>();
	}
	
	public Assignment(AssignmentState initial, AssignmentState expected) {
		_initialState = initial;
		_currentState = initial;
		_expectedAnswer = expected;
		_moduleList = new HashMap<String, AssignmentModule>();
	}
	
	public AssignmentState getInitialState() {
		return _initialState;
	}
	
	public AssignmentState getCurrentState() {
		return _currentState;
	}
	
	public AssignmentState getExpectedAnswer() {
		return _expectedAnswer;
	}

	public void addModule(AssignmentModule module) {
		_moduleList.put(module.getName(), (AssignmentModule)module.clone());
	}
	
	public HashMap<String, AssignmentModule> getModuleList() {
		return _moduleList;
	}
	
}

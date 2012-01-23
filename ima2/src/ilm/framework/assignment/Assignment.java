package ilm.framework.assignment;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.modules.AssignmentModule;

import java.util.HashMap;

public class Assignment {

	private String _proposition;
	private AssignmentState _initialState;
	private AssignmentState _currentState;
	private AssignmentState _expectedAnswer;
	private HashMap<String, AssignmentModule> _moduleList;
	private HashMap<String, String> _config;
	
	public Assignment(String proposition, AssignmentState initial, 
					  AssignmentState current, AssignmentState expected) {
		_proposition = proposition;
		_initialState = initial;
		_currentState = current;
		_expectedAnswer = expected;
		_moduleList = new HashMap<String, AssignmentModule>();
		_config = new HashMap<String, String>();
	}
	
	public String getProposition() {
		return _proposition;
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
		_moduleList.put(module.getName(), module);
		if(module.getObserverType() != AssignmentModule.ACTION_OBSERVER) {
			_currentState.addObserver(module);
		}
	}
	
	public HashMap<String, AssignmentModule> getModuleList() {
		return _moduleList;
	}
	
	public void setConfigParameter(String key, String value) {
		_config.put(key, value);
	}
	
	public String getConfigParameter(String key) {
		return _config.get(key);
	}

	public void setConfig(HashMap<String, String> config) {
		_config = config;
	}
	
}

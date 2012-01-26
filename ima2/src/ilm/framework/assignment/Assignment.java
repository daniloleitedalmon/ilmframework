package ilm.framework.assignment;

import ilm.framework.assignment.model.AssignmentState;

import java.util.HashMap;

public class Assignment {

	private String _proposition;
	private AssignmentState _initialState;
	private AssignmentState _currentState;
	private AssignmentState _expectedAnswer;
	private HashMap<String, String> _config;
	private HashMap<String, String> _metadata;
	
	public Assignment(String proposition, AssignmentState initial, 
					  AssignmentState current, AssignmentState expected) {
		_proposition = proposition;
		_initialState = initial;
		_currentState = current;
		_expectedAnswer = expected;
		_config = new HashMap<String, String>();
		_metadata = new HashMap<String, String>();
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
	
	
	public void setConfigParameter(String key, String value) {
		_config.put(key, value);
	}
	
	public String getConfigParameter(String key) {
		return _config.get(key);
	}

	public void setConfig(HashMap<String, String> config) {
		_config = config;
	}
	
	
	public void setMetadataParameter(String key, String value) {
		_metadata.put(key, value);
	}
	
	public String getMetadataParameter(String key) {
		return _metadata.get(key);
	}
	
	public void setMetadata(HashMap<String, String> metadata) {
		_metadata = metadata;
	}
	
}

package ilm.framework.assignment;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;

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
	
	public HashMap<String, String> getConfig() {
		return _config;
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
	
	public HashMap<String, String> getMetadata() {
		return _metadata;
	}
	
	public void print() {
		System.out.println("Proposition:" + _proposition);
		System.out.println("Initial:");
		for(DomainObject obj : _initialState.getList()) {
			System.out.println(obj.getName() + " " + obj.getDescription());
		}
		System.out.println("Current:");
		for(DomainObject obj : _currentState.getList()) {
			System.out.println(obj.getName() + " " + obj.getDescription());
		}
		if(_expectedAnswer != null) {
			System.out.println("Expected:");
			for(DomainObject obj : _expectedAnswer.getList()) {
				System.out.println(obj.getName() + " " + obj.getDescription());
			}
		}
		System.out.println("Config:");
		for(String key : _config.keySet()) {
			System.out.println(key + ": " + _config.get(key));
		}
		System.out.println("Metadata:");
		for(String key : _metadata.keySet()) {
			System.out.println(key + ": " + _metadata.get(key));
		}
	}
	
}

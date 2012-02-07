package ilm.framework.gui;

import java.util.HashMap;
import java.util.Observer;

import ilm.framework.assignment.Assignment;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainGUI;

import javax.swing.JFrame;

public abstract class AuthoringGUI extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	protected DomainGUI _domainGUI;
	protected Assignment _assignment;
	protected HashMap<String, String> _config;
	protected HashMap<String, String> _metadata;

	public void setComponents(HashMap<String, String> config, DomainGUI domainGUI, HashMap<String, String> metadata) {
		_config = config;
		_domainGUI = domainGUI;
		_domainGUI.getCurrentState().addObserver(this);
		_metadata = metadata;
	}
	
	public Assignment getAssignment() {
		_assignment = new Assignment(getProposition(), 
									 getInitialState(), 
									 getInitialState(), 
									 getExpectedAnswer());
		_assignment.setName(getAssignmentName());
		_assignment.setConfig(getConfig());
		_assignment.setMetadata(getMetadata());
		return _assignment;
	}

	protected abstract String getProposition();

	protected abstract String getAssignmentName();
	
	protected abstract AssignmentState getInitialState();

	protected abstract AssignmentState getExpectedAnswer();
	
	protected abstract HashMap<String, String> getConfig();
	
	protected abstract HashMap<String, String> getMetadata();

	public void setAssignment(String proposition, AssignmentState initial,
			AssignmentState current, AssignmentState expected) {
		_assignment = new Assignment(proposition, initial, current, expected);
		initFields();
	}
	
	protected abstract void initFields();

}

package ilm.framework.gui;

import java.util.HashMap;

import ilm.framework.assignment.Assignment;
import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainGUI;

import javax.swing.JFrame;

public abstract class AuthoringGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private IAssignment _authoringCommands;
	protected DomainGUI _domainGUI;
	protected Assignment _assignment;
	protected HashMap<String, String> _config;
	protected HashMap<String, String> _metadata;

	public void setDomainGUI(DomainGUI domainGUI) {
		_domainGUI = domainGUI;
	}

	public void setAssignmentCommands(IAssignment commands) {
		_authoringCommands = commands;
	}
	
	public void setConfig(HashMap<String, String> config) {
		_config = config;
	}
	
	public void setMetadata(HashMap<String, String> metadata) {
		_metadata = metadata;
	}
	
	protected void finishAuthoring() {
		_assignment = new Assignment(getProposition(), 
									 getInitialState(), 
									 getInitialState(), 
									 getExpectedAnswer());
		_assignment.setConfig(getConfig());
		_assignment.setMetadata(getMetadata());
		_authoringCommands.authorAssignment(_assignment);
	}

	protected abstract String getProposition();

	protected abstract AssignmentState getInitialState();

	protected abstract AssignmentState getExpectedAnswer();
	
	protected abstract HashMap<String, String> getConfig();
	
	protected abstract HashMap<String, String> getMetadata();

}

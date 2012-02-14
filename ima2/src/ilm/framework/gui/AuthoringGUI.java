package ilm.framework.gui;

import java.util.HashMap;
import java.util.Observer;

import ilm.framework.assignment.Assignment;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainGUI;

import javax.swing.JFrame;

public abstract class AuthoringGUI extends JFrame implements Observer {

	/**
	 * @attribute serial version due to javax.swing specification
	 * @attribute the domain user interface this authoring interface is attached to
	 * @attribute the assignment that is being authored
	 * @attribute the assignemnt configuration
	 * @attribute the assignment metadata
	 * 
	 * TODO maybe put the config and the metadata inside the assignment already
	 */
	private static final long serialVersionUID = 1L;
	protected DomainGUI _domainGUI;
	protected Assignment _assignment;
	protected HashMap<String, String> _config;
	protected HashMap<String, String> _metadata;

	/**
	 * Defines and initializes the components on which the assignment will be authored
	 * @param assignment config
	 * @param domainGUI
	 * @param assignment metadata
	 * It is called just after the construct method by the framework
	 */
	public final void setComponents(HashMap<String, String> config, DomainGUI domainGUI, 
									HashMap<String, String> metadata) {
		_config = config;
		_domainGUI = domainGUI;
		_domainGUI.getCurrentState().addObserver(this);
		_metadata = metadata;
	}

	/**
	 * Defines and initializes the assignment that will be authored
	 * @param proposition
	 * @param initial assignment state
	 * @param current assignment state
	 * @param expected assignment answer
	 * It is called just after the construct method by the framework
	 */
	public final void setAssignment(String proposition, AssignmentState initial,
							  AssignmentState current, AssignmentState expected) {
		_assignment = new Assignment(proposition, initial, current, expected);
		initFields();
	}
	
	/**
	 * Initialization method to the user interface features (widgets) 
	 * that depend on the assignment to be defined. It is called at the
	 * method "setAssignment", just after the assignment is defined.
	 * 
	 * @see example.ilm.gui.IlmAuthoringGUI
	 */
	protected abstract void initFields();
	
	/**
	 * @return Template Method that creates the authored assignment
	 * calling up various Hook Methods that define each one of the
	 * assignment's field. It is called by BaseGUI when the button
	 * save assignment is pressed.
	 */
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

	/**
	 * Hook Method for defining the assignment's proposition
	 * @return the assignment's proposition
	 */
	protected abstract String getProposition();

	/**
	 * Hook Method for defining the assignment's name
	 * @return the assignment's name
	 */
	protected abstract String getAssignmentName();
	
	/**
	 * Hook Method for defining the assignment's initial state
	 * @return the initial state
	 * It must be implemented by any method using domain specific
	 * informations.
	 * 
	 * @see example.ilm.gui.IlmAuthoringGUI
	 */
	protected abstract AssignmentState getInitialState();

	/**
	 * Hook Method for defining the assignment's expected answer
	 * @return the expected answer
	 * It must be implemented by any method using domain specific
	 * informations.
	 * 
	 * @see example.ilm.gui.IlmAuthoringGUI
	 */
	protected abstract AssignmentState getExpectedAnswer();
	
	/**
	 * Hook Method for defining the assignment's configuration
	 * @return the configuration
	 * It must be implemented by any method using domain specific
	 * informations.
	 * 
	 * @see example.ilm.gui.IlmAuthoringGUI
	 */
	protected abstract HashMap<String, String> getConfig();
	
	/**
	 * Hook Method for defining the assignment's metadata
	 * @return the metadata
	 * It must be implemented by any method using domain specific
	 * informations.
	 * 
	 * @see example.ilm.gui.IlmAuthoringGUI
	 */
	protected abstract HashMap<String, String> getMetadata();

}

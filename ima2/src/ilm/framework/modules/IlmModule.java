package ilm.framework.modules;

import javax.swing.JPanel;

import ilm.framework.assignment.IAssignmentList;
import ilm.framework.assignment.IAssignmentOperator;

public abstract class IlmModule {

	protected String _name;
	protected int _assignmentIndex;
	protected IAssignmentList _assignmentList;
	protected IAssignmentOperator _operator;
	protected JPanel _gui;
	
	public IlmModule(IAssignmentList assignments, IAssignmentOperator operator) {
		_assignmentList = assignments;
		_operator = operator;
	}
	
	public String getName() {
		return _name;
	}
	
	public JPanel getGUI() {
		return _gui;
	}
	
	public void setAssignmentIndex(int index) {
		_assignmentIndex = index;
	}
	
}

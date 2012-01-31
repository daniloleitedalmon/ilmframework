package ilm.framework.modules;

import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.IAssignmentOperator;

public abstract class OperationModule extends IlmModule {

	protected IAssignment _assignmentList;
	protected IAssignmentOperator _operator;
	
	public void setAssignmentList(IAssignment a) {
		_assignmentList = a;
	}
	
	public void setAssignmentOperator(IAssignmentOperator o) {
		_operator = o;
	}
	
}

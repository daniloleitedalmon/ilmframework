package ilm.modules.scorm;

import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.IAssignmentOperator;
import ilm.framework.modules.OperationModule;

public class ScormModule extends OperationModule {

	public ScormModule(IAssignment assignments, IAssignmentOperator operator) {
		setAssignmentList(assignments);
		setAssignmentOperator(operator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

}

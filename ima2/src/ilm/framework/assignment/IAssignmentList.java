package ilm.framework.assignment;

import ilm.framework.assignment.model.AssignmentState;

public interface IAssignmentList {
	
	public AssignmentState getCurrentState(int index);
	
	public AssignmentState getInitialState(int index);
	
	public AssignmentState getExpectedAnswer(int index);
	
}

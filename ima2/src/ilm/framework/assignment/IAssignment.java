package ilm.framework.assignment;

import ilm.framework.assignment.model.AssignmentState;

public interface IAssignment {
	
	public AssignmentState getCurrentState(int index);
	
	public AssignmentState getInitialState(int index);
	
	public AssignmentState getExpectedAnswer(int index);
	
	public int authorAssignment(Assignment assignment);
	
}

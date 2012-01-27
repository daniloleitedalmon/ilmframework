package ilm.framework.assignment;

import java.util.HashMap;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.modules.IlmModule;

public interface IAssignment {
	
	public AssignmentState getCurrentState(int index);
	
	public AssignmentState getInitialState(int index);
	
	public AssignmentState getExpectedAnswer(int index);
	
	public int authorAssignment(Assignment assignment);

	public HashMap<String, IlmModule> getIlmModuleList();
	
}

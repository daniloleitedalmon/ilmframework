package ilm.framework.assignment;

import java.util.ArrayList;
import java.util.HashMap;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.modules.IlmModule;

public interface IAssignment {
	
	public AssignmentState getCurrentState(int index);
	
	public AssignmentState getInitialState(int index);
	
	public AssignmentState getExpectedAnswer(int index);
	
	public HashMap<String, IlmModule> getIlmModuleList();
	
	public int getNumberOfAssignments();
	
	public ArrayList<AssignmentState> openAssignmentFile(String fileName);
	
	public AssignmentState newAssignment();
	
	public void closeAssignment(int index);

	public int authorAssignment(Assignment assignment);

}

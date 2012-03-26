package ilm.framework.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipFile;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.modules.IlmModule;

public interface IAssignment {

	public String getProposition(int index);
	
	public AssignmentState getCurrentState(int index);
	
	public AssignmentState getInitialState(int index);
	
	public AssignmentState getExpectedAnswer(int index);
	
	public HashMap<String, String> getConfig(int index);
	
	public HashMap<String, String> getMetadata(int index);
	
	
	public HashMap<String, IlmModule> getIlmModuleList();
	
	public int getNumberOfAssignments();
	
	
	public int openAssignmentPackage(String fileName);
	
	public ZipFile saveAssignmentPackage(ArrayList<Assignment> assignmentList, String fileName);
	
	public AssignmentState newAssignment();
	
	public void closeAssignment(int index);

}

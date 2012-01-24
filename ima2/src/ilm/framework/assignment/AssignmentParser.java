package ilm.framework.assignment;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.modules.AssignmentModule;
import ilm.framework.domain.DomainConverter;

import java.util.ArrayList;
import java.util.HashMap;

final class AssignmentParser {

	public Assignment convertStringToAssignment(DomainConverter converter,	String assignmentString) {
		String proposition = getProposition(assignmentString);
		AssignmentState initialState = getInitialState(converter, assignmentString);
		AssignmentState currentState = getCurrentState(converter, assignmentString);
		AssignmentState expectedState = getExpectedAnswer(converter, assignmentString);
		ArrayList<AssignmentModule> moduleList = getModuleList(converter, assignmentString);
		HashMap<String, String> config = getConfig(assignmentString);
		HashMap<String, String> metadata = getMetadata(assignmentString);
		
		Assignment assignment = new Assignment(proposition, initialState, currentState, expectedState);
		
		for(AssignmentModule m : moduleList) {
			assignment.addModule(m);
		}
		assignment.setConfig(config);
		assignment.setMetadata(metadata);
		return assignment;
	}
	
	private ArrayList<AssignmentModule> getModuleList(DomainConverter converter, String assignmentString) {
		// TODO Auto-generated method stub
		return null;
	}

	private AssignmentState getCurrentState(DomainConverter converter, String assignmentString) {
		// TODO Auto-generated method stub
		return null;
	}

	private AssignmentState getExpectedAnswer(DomainConverter converter, String assignmentString) {
		// TODO Auto-generated method stub
		return null;
	}

	private AssignmentState getInitialState(DomainConverter converter, String assignmentString) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getProposition(String assignmentString) {
		// TODO Auto-generated method stub
		return null;
	}

	private  HashMap<String, String> getConfig(String assignmentString) {
		// TODO Auto-generated method stub
		return null;
	}

	private HashMap<String, String> getMetadata(String assignmentString) {
		// TODO Auto-generated method stub
		return null;
	}

}

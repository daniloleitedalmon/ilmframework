package ilm.model;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainModel;

public class IlmDomainModel extends DomainModel {

	private int _objCount = 1;
	
	void AddSubString(AssignmentState state, String substring) {
	    String name = substring + _objCount;	    
	    _objCount++;
	    ObjectSubString objectSubString = new ObjectSubString(name, substring, substring);
	    state.add(objectSubString);
	}

	void RemoveSubString(AssignmentState state) {
	    state.remove(state.size()-1);
	}
	

	@Override
	public AssignmentState getNewAssignmentState() {
		return new AssignmentState();
	}

	@Override
	public float AutomaticChecking(AssignmentState currentState, AssignmentState expectedAnswer) {
		// TODO Auto-generated method stub
		return 0;
	}

}
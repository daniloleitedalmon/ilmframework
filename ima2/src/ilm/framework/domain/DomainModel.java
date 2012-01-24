package ilm.framework.domain;

import ilm.framework.assignment.model.AssignmentState;

public abstract class DomainModel {
	
    public abstract AssignmentState getNewAssignmentState();
    
    public abstract float AutomaticChecking(AssignmentState currentState, AssignmentState expectedAnswer);

}

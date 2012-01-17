package ilm.framework.domain;

import ilm.framework.assignment.model.AssignmentState;

public abstract class DomainModel implements DomainConverter {
	
    public abstract AssignmentState getNewAssignmentState();
    
    public abstract float AutomaticChecking(AssignmentState currentState, AssignmentState expectedAnswer);

}

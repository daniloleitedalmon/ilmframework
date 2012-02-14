package ilm.framework.domain;

import ilm.framework.assignment.model.AssignmentState;

public abstract class DomainModel {
	
	/**
	 * @return an assignment state representing a new blank assignment
	 * An assignment state is basically a list of DomainObject with
	 * some operations implemented.
	 * 
	 * @see example.ilm.model.IlmDomainModel
	 */
    public abstract AssignmentState getNewAssignmentState();
    
    /**
     * Automatically checks the correctedness of the answer made by the student by comparing
     * it with the teacher's expected answer. This method must be implemented using any technique
     * that makes sense whithin the system's domain.
     * @param studentAnswer
     * @param expectedAnswer
     * @return the percentage (grade) as the result of the comparison between the currentState,
     * sent by the student, and the expected answer, defined by the teacher
     * 
     * @see example.ilm.model.IlmDomainModel
     */
    public abstract float AutomaticChecking(AssignmentState studentAnswer, AssignmentState expectedAnswer);

}

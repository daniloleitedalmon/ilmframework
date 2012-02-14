package example.ilm.model;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainModel;

public class IlmDomainModel extends DomainModel {

	/**
	 * This counter is used to define unique names for the 
	 * objects being created
	 */
	private int _objCount = 1;
	
	/**
	 * Adds an ObjectSubString to the assignment state with the parameter substring
	 * @param state
	 * @param substring
	 * 
	 * @see example.ilm.model.ActionAddSubString, it is called when this
	 * action is executed
	 * @see example.ilm.model.ActionRemoveSubString, it is called when this
	 * action is undone
	 */
	public void AddSubString(AssignmentState state, String substring) {
	    String name = substring + _objCount;	    
	    _objCount++;
	    ObjectSubString objectSubString = new ObjectSubString(name, substring, substring);
	    state.add(objectSubString);
	}

	/**
	 * Remove the last ObjectSubString from the assignment state
	 * @param state
	 * 
	 * @see example.ilm.model.ActionAddSubString, it is called when this
	 * action is undone
	 * @see example.ilm.model.ActionRemoveSubString, it is called when this
	 * action is executed
	 */
	public void RemoveSubString(AssignmentState state) {
	    state.remove(state.getList().size()-1);
	}
	
	/**
	 * @return an assignment state representing a new blank assignment.
	 * In this case, a blank assignment has no objects in it.
	 */
	@Override
	public AssignmentState getNewAssignmentState() {
		return new AssignmentState();
	}

	/**
	 * @return the result of the comparison between the student state
	 * with the expected state.
	 */
	@Override
	public float AutomaticChecking(AssignmentState student, 
								   AssignmentState expected) {
		if(student.getList().size() != expected.getList().size()) {
			return 0;
		}
		int grade = 0;
		for(int i = 0; i < student.getList().size(); i++) {
			if(student.get(i).equals(expected.get(i))) {
				grade++;
			}
		}
		return grade/student.getList().size();
	}

}
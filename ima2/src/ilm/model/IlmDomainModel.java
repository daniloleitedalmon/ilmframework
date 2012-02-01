package ilm.model;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainModel;

public class IlmDomainModel extends DomainModel {

	private int _objCount = 1;
	
	public void AddSubString(AssignmentState state, String substring) {
	    String name = substring + _objCount;	    
	    _objCount++;
	    ObjectSubString objectSubString = new ObjectSubString(name, substring, substring);
	    state.add(objectSubString);
	}

	public void RemoveSubString(AssignmentState state) {
	    state.remove(state.getList().size()-1);
	}
	
	@Override
	public AssignmentState getNewAssignmentState() {
		return new AssignmentState();
	}

	@Override
	public float AutomaticChecking(AssignmentState cur, AssignmentState expected) {
		if(cur.getList().size() != expected.getList().size()) {
			return 0;
		}
		int grade = 0;
		for(int i = 0; i < cur.getList().size(); i++) {
			if(cur.get(i).equals(expected.get(i))) {
				grade++;
			}
		}
		return grade/cur.getList().size();
	}

}
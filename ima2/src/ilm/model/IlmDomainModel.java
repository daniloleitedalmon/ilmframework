package ilm.model;

import java.util.ArrayList;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.assignment.modules.AssignmentModule;
import ilm.framework.domain.DomainModel;

public class IlmDomainModel extends DomainModel {

	@Override
	public AssignmentState getNewAssignmentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float AutomaticChecking(AssignmentState currentState, AssignmentState expectedAnswer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AssignmentState convertStringToAssignment(String stateDescription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convertAssignmentToString(AssignmentState state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<DomainObject> convertStringToObject(
			String objectListDescription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convertObjectToString(ArrayList<DomainObject> objectList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<DomainAction> convertStringToAction(
			String actionListDescription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convertActionToString(ArrayList<DomainAction> actionList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AssignmentModule> convertStringToModuleList(
			ArrayList<String> moduleListDescription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> convertModuleListToString(
			ArrayList<AssignmentModule> moduleList) {
		// TODO Auto-generated method stub
		return null;
	}

}

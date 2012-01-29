package ilm.framework.modules;

import java.util.ArrayList;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.IAssignmentOperator;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainModel;

public class AutomaticCheckingModule extends OperationModule implements IlmProtocol {

	private DomainModel _model;
	
	public AutomaticCheckingModule(IAssignment assignments, IAssignmentOperator operator) {
		setAssignmentList(assignments);
		setAssignmentOperator(operator);
		_name = "automatic_checking";
	}
	
	@Override
	public float getEvaluation() {
		return _model.AutomaticChecking(_assignmentList.getCurrentState(_assignmentIndex),
										_assignmentList.getExpectedAnswer(_assignmentIndex));
	}

	@Override
	public String getAnswer() {
		ArrayList<DomainObject> list = new ArrayList<DomainObject>();
		for(int i = 0; i < _assignmentList.getCurrentState(_assignmentIndex).size(); i++) {
			list.add(_assignmentList.getCurrentState(_assignmentIndex).get(i));
		}
		return _operator.getConverter().convertObjectToString(list);
	}

	public void setModel(DomainModel model) {
		_model = model;
	}

}

package ilm.framework.modules;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.IAssignmentOperator;
import ilm.framework.domain.DomainModel;

public class AutomaticCheckingModule extends IlmModule implements IlmProtocol {

	private DomainModel _model;
	
	public AutomaticCheckingModule(IAssignment assignments, IAssignmentOperator operator) {
		super(assignments, operator);
		_name = "automatic_checking";
	}
	
	@Override
	public float getEvaluation() {
		return _model.AutomaticChecking(_assignmentList.getCurrentState(_assignmentIndex),
										_assignmentList.getExpectedAnswer(_assignmentIndex));
	}

	@Override
	public String getAnswer() {
		return _operator.getConverter().convertAssignmentToString(
							_assignmentList.getCurrentState(_assignmentIndex));
	}

	public void setModel(DomainModel model) {
		_model = model;
	}

}

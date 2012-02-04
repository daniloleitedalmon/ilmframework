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
		_name = IlmProtocol.AUTO_CHECKING_MODULE_NAME;
		_gui = new AutoCheckingModuleToolbar(this);
	}
	
	@Override
	public float getEvaluation() {
		if(_assignmentList.getExpectedAnswer(_assignmentIndex) == null) {
			return 0;
		}
		return _model.AutomaticChecking(_assignmentList.getCurrentState(_assignmentIndex),
										_assignmentList.getExpectedAnswer(_assignmentIndex));
	}

	@Override
	public String getAnswer() {
		ArrayList<DomainObject> list = _assignmentList.getCurrentState(_assignmentIndex).getList();
		return _operator.getConverter().convertObjectToString(list);
	}

	public void setModel(DomainModel model) {
		_model = model;
	}

	public void print() {
		System.out.println("Name: " + _name + " index: " + _assignmentIndex + " model: " + _model.toString());
	}

	public boolean hasExpectedAnswer() {
		if(_assignmentList.getExpectedAnswer(_assignmentIndex) == null) {
			return false;
		}
		return true;
	}
	
}

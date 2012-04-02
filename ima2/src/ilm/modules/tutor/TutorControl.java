package ilm.modules.tutor;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.modules.OperationModule;

public class TutorControl extends OperationModule {

	protected TutorActionDecider _tutorActionDecider;
	protected TutorAction _tutorAction;

	public TutorControl()
	{
		_name = IlmProtocol.TUTOR_MODULE_NAME;
		_gui = new TutorToolbar(this);
		_tutorAction = null;
	}

	public void executeTutorAction()
	{
		if (_tutorAction != null && !_tutorAction.getIsImmediateAction()) {
			
		}
	}
	public void actionPerformed(DomainAction action)
	{
		_tutorAction = _tutorActionDecider.getTutorAction(_assignmentList.getCurrentState(_assignmentIndex), action);
		if (_tutorAction.getIsImmediateAction()) {
			_tutorAction.execute();
		}
	}
	
	public void setActionDecider(TutorActionDecider decider)
	{
		_tutorActionDecider = decider;
	}
	
	public boolean hasAvailableTutorAction()
	{
		return _tutorActionDecider.hasAvailableTutorAction();
	}
	
	@Override
	public void print() {
		System.out.println("Name: " + _name + " index: " + _assignmentIndex + " Action Decider: " + _tutorActionDecider.toString());
	}
}

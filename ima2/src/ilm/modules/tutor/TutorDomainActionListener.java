package ilm.modules.tutor;

import java.util.Collection;
import java.util.Observable;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

public class TutorDomainActionListener extends AssignmentModule {

	private TutorControl _control;
	
	public TutorDomainActionListener(TutorControl control)
	{
		_control = control;
	}
	
	@Override
	public void update(Observable action, Object o) {
		_control.actionPerformed((DomainAction)action);
	}

	@Override
	public void setDomainModel(DomainModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setState(AssignmentState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActionObservers(Collection<IlmModule> values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAssignment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAssignment(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContentFromString(DomainConverter converter, int index,
			String moduleContent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStringContent(DomainConverter converter, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

}

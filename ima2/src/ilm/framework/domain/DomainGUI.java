package ilm.framework.domain;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.modules.AssignmentModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Observer;

import javax.swing.JPanel;

public abstract class DomainGUI extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	protected AssignmentState _state;
	protected DomainModel _model;
	protected HashMap<String, DomainAction> _actionList;
	
	public void setDomainModel(DomainModel model) {
		_model = model;
	}
	
	public void setAssignment(AssignmentState curState, Collection<AssignmentModule> moduleList) {
		_state = curState;
		for(DomainAction action: _actionList.values()) {
			action.setState(_state);
			action.deleteObservers();
			for(AssignmentModule module: moduleList) {
				if(module.getObserverType() != AssignmentModule.OBJECT_OBSERVER) {
					action.addObserver(module);
				}
			}
		}
		_state.addObserver(this);
	}
	
	public abstract void setAssignmentModulesGUI(Collection<AssignmentModule> moduleList);

}

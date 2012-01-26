package ilm.framework.domain;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

import java.util.ArrayList;
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
	
	public void setAssignment(AssignmentState curState, Collection<IlmModule> moduleList) {
		_state = curState;
		_state.addObserver(this);
		for(DomainAction action: _actionList.values()) {
			action.setState(_state);
			action.deleteObservers();
			for(IlmModule module: moduleList) {
				if(module instanceof AssignmentModule) {
					AssignmentModule assMod = (AssignmentModule)module;
					if(assMod.getObserverType() != AssignmentModule.OBJECT_OBSERVER) {
						action.addObserver(assMod);
					}
				}
			}
		}
	}
	
	public abstract void setAssignmentModulesGUI(Collection<AssignmentModule> moduleList);

	public abstract ArrayList<DomainObject> getSelectedObjects();
	
}

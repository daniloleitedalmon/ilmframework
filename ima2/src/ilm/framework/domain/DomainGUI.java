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

	protected String _proposition;
	protected AssignmentState _state;
	protected HashMap<String, DomainAction> _actionList;
	
	public void setAssignment(String proposition, AssignmentState curState, 
								Collection<IlmModule> moduleList) {
		_proposition = proposition;
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
		initDomainGUI();
	}
	    
	protected abstract void initDomainGUI();

	public abstract void setDomainModel(DomainModel model);
	
	public abstract ArrayList<DomainObject> getSelectedObjects();
	
	public abstract AssignmentState getCurrentState();
	
}

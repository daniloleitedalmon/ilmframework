package ilm.framework.domain;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.modules.AssignmentModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

import javax.swing.JPanel;

public abstract class DomainGUI extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	protected DomainModel _model;
	protected HashMap<String, DomainAction> _actionList;
	
	public void setDomainModel(DomainModel model) {
		_model = model;
	}
	
	public void setAssignment(AssignmentState curState, ArrayList<AssignmentModule> moduleList) {
		for(DomainAction action: _actionList.values()) {
			action.setState(curState);
			action.deleteObservers();
			for(AssignmentModule module: moduleList) {
				action.addObserver(module);
			}
		}
		curState.addObserver(this);
	}
	
	public abstract void setAssignmentModulesGUI(ArrayList<AssignmentModule> moduleList);

}

package ilm.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;

public class IlmDomainGUI extends DomainGUI {

	private static final long serialVersionUID = 1L;

	public IlmDomainGUI(DomainModel model) {
		_model = model;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAssignmentModulesGUI(Collection<AssignmentModule> moduleList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<DomainObject> getSelectedObjects() {
		// TODO Auto-generated method stub
		return null;
	}

}

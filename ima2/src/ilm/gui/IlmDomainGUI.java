package ilm.gui;

import java.util.ArrayList;
import java.util.Observable;

import ilm.framework.assignment.modules.AssignmentModule;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;

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
	public void setAssignmentModulesGUI(ArrayList<AssignmentModule> moduleList) {
		// TODO Auto-generated method stub
		
	}

}

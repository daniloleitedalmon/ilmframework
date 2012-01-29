package ilm.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.model.ActionAddSubString;
import ilm.model.ActionRemoveSubString;
import ilm.model.IlmDomainModel;

public class IlmDomainGUI extends DomainGUI {

	private static final long serialVersionUID = 1L;
	private IlmDomainModel _model;
	private IlmDomainPanel _panel;
	
	public IlmDomainGUI() {
		_actionList = new HashMap<String, DomainAction>();
		ActionAddSubString addAction = new ActionAddSubString("add", "add");
		ActionRemoveSubString delAction = new ActionRemoveSubString("del", "del");
		addAction.setDomain(_model);
		delAction.setDomain(_model);
		_actionList.put(addAction.getName(), addAction);
		_actionList.put(delAction.getName(), delAction);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<DomainObject> getSelectedObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDomainModel(DomainModel model) {
		_model = (IlmDomainModel)model;
	}

	public void RemoveSubString() {
		// TODO Auto-generated method stub
		
	}

	public void AddSubString() {
		// TODO Auto-generated method stub
		
	}

}
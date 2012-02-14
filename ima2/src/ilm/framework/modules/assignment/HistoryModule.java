package ilm.framework.modules.assignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

public class HistoryModule extends AssignmentModule {

	private ArrayList<ArrayList<DomainAction>> _history;
	
	public HistoryModule() {
		_history = new ArrayList<ArrayList<DomainAction>>();
		_name = IlmProtocol.HISTORY_MODULE_NAME;
		_gui = new HistoryModuleToolbar();
		_observerType = ACTION_OBSERVER;
	}
	
	ArrayList<DomainAction> getHistory() {
		return _history.get(_assignmentIndex);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof DomainAction) {
			DomainAction action = (DomainAction)o;
			
			if(!action.isUndo()) {
				_history.get(_assignmentIndex).add((DomainAction)action.clone());
				setChanged();
				notifyObservers();
			}
			else {
				_history.get(_assignmentIndex).remove(_history.get(_assignmentIndex).size()-1);
				setChanged();
				notifyObservers();
			}
		}
	}

	@Override
	public void setContentFromString(DomainConverter converter,	int index, String moduleContent) {
		if(_history.size() == index) {
			addAssignment();
		}
		for(DomainAction action : converter.convertStringToAction(moduleContent)) {
			action.addObserver(this);
			_history.get(index).add(action);
		}
	}

	@Override
	public void addAssignment() {
		_history.add(new ArrayList<DomainAction>());
	}

	@Override
	public void print() {
		System.out.println("Name: " + _name + " size: " + _history.size());
		for(ArrayList<DomainAction> list : _history) {
			System.out.println("size: " + list.size());
			for(DomainAction a : list) {
				System.out.println(a.getName() + " " + a.getDescription());
			}
		}
	}

	@Override
	public String getStringContent(DomainConverter converter, int index) {
		if(_history.get(_assignmentIndex).size() == 0) {
			return "<" + _name + "/>";
		}
		String string = "<" + _name + ">";
		for(DomainAction action : _history.get(index)) {
			string += action.toString();
		}
		string += "</" + _name + ">";
		return string;
	}

	@Override
	public void removeAssignment(int index) {
		_history.remove(index);
	}

	@Override
	public void setDomainModel(DomainModel model) {
		for(ArrayList<DomainAction> list : _history) {
			for(DomainAction action : list) {
				action.setDomainModel(model);
			}
		}
	}

	@Override
	public void setState(AssignmentState state) {
		for(DomainAction action : _history.get(_history.size()-1)) {
			action.setState(state);
		}
	}

	@Override
	public void setActionObservers(Collection<IlmModule> values) {
		for(IlmModule m : values) {
			if(m instanceof AssignmentModule) {
				for(ArrayList<DomainAction> list : _history) {
					for(DomainAction action : list) {
						action.addObserver((AssignmentModule)m);
					}
				}
			}
		}
	}

}

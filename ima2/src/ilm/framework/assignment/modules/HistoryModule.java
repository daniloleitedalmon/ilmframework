package ilm.framework.assignment.modules;

import java.util.ArrayList;
import java.util.Observable;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;

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
			
			if(action.isUndo()) {
				_history.get(_assignmentIndex).add(action);
				setChanged();
				notifyObservers();
			}
			else {
				_history.get(_assignmentIndex).remove(action);
				setChanged();
				notifyObservers();
			}
		}
	}

	@Override
	public void setContentFromString(DomainConverter converter,	String moduleContent) {
		_history.add(converter.convertStringToAction(moduleContent));
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

}

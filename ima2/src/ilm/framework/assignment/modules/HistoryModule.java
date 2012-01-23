package ilm.framework.assignment.modules;

import java.util.ArrayList;
import java.util.Observable;

import ilm.framework.assignment.model.DomainAction;

public class HistoryModule extends AssignmentModule {

	private ArrayList<DomainAction> _history;
	
	public HistoryModule() {
		_history = new ArrayList<DomainAction>();
		
		_name = "history";
		_gui = new HistoryModuleToolbar();
		_observerType = ACTION_OBSERVER;
	}
	
	ArrayList<DomainAction> getHistory() {
		return _history;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof DomainAction) {
			DomainAction action = (DomainAction)o;
			
			if(action.isUndo()) {
				_history.add(action);
				setChanged();
				notifyObservers();
			}
			else {
				_history.remove(action);
				setChanged();
				notifyObservers();
			}
		}
	}

}

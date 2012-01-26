package ilm.framework.assignment.modules;

import java.util.ArrayList;
import java.util.Observable;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;

public class ObjectListModule extends AssignmentModule {

	private ArrayList<DomainObject> _objectList;
	
	public ObjectListModule() {
		_objectList = new ArrayList<DomainObject>();
		
		_name = "object_list";
		_gui = new ObjectListModuleToolbar();
		_observerType = OBJECT_OBSERVER;
	}
	
	ArrayList<DomainObject> getObjectList() {
		return _objectList;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof AssignmentState) {
			AssignmentState state = (AssignmentState)o;
			
			// TODO need a better non-brute force way to do this
			_objectList.clear();
			for(int i = 0; i < state.size(); i++) { 
				_objectList.add(state.get(i));
			}
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public void setContentFromString(DomainConverter converter,
			String moduleContent) {
		// TODO Auto-generated method stub
		
	}

}

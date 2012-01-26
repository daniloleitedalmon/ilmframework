package ilm.framework.assignment.modules;

import java.util.ArrayList;
import java.util.Observable;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;

public class ObjectListModule extends AssignmentModule {

	private ArrayList<ArrayList<DomainObject>> _objectList;
	
	public ObjectListModule() {
		_objectList = new ArrayList<ArrayList<DomainObject>>();
		
		_name = "object_list";
		_gui = new ObjectListModuleToolbar();
		_assignmentIndex = 0;
		_observerType = OBJECT_OBSERVER;
	}
	
	ArrayList<DomainObject> getObjectList() {
		return _objectList.get(_assignmentIndex);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof AssignmentState) {
			AssignmentState state = (AssignmentState)o;
			
			// TODO need a better non-brute force way to do this
			_objectList.get(_assignmentIndex).clear();
			for(int i = 0; i < state.size(); i++) { 
				_objectList.get(_assignmentIndex).add(state.get(i));
			}
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public void setContentFromString(DomainConverter converter,	String moduleContent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAssignment() {
		_objectList.add(new ArrayList<DomainObject>());
	}

}

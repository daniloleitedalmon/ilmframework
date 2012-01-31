package ilm.framework.assignment.modules;

import java.util.ArrayList;
import java.util.Observable;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;

public class ObjectListModule extends AssignmentModule {

	private ArrayList<ArrayList<DomainObject>> _objectList;
	
	public ObjectListModule() {
		_objectList = new ArrayList<ArrayList<DomainObject>>();
		
		_name = IlmProtocol.OBJECT_LIST_MODULE_NAME;
		_gui = new ObjectListModuleToolbar();
		_assignmentIndex = 0;
		_observerType = OBJECT_OBSERVER;
	}
	
	public ArrayList<DomainObject> getObjectList() {
		return _objectList.get(_assignmentIndex);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof AssignmentState) {
			AssignmentState state = (AssignmentState)o;
			
			// TODO need a better non-brute force way to do this
			_objectList.get(_assignmentIndex).clear();
			for(DomainObject obj : state.getList()) { 
				_objectList.get(_assignmentIndex).add(obj);
			}
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public void setContentFromString(DomainConverter converter,	String moduleContent) {
		_objectList.add(converter.convertStringToObject(moduleContent));
	}

	@Override
	public void addAssignment() {
		_objectList.add(new ArrayList<DomainObject>());
	}

}

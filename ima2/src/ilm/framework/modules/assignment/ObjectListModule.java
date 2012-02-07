package ilm.framework.modules.assignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

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
	public void setContentFromString(DomainConverter converter,	int index, String moduleContent) {
		if(_objectList.size() == index) {
			addAssignment();
		}
		for(DomainObject obj : converter.convertStringToObject(moduleContent)) {
			_objectList.get(index).add(obj);
		}
	}

	@Override
	public void addAssignment() {
		_objectList.add(new ArrayList<DomainObject>());
	}

	@Override
	public void print() {
		System.out.println("Name: " + _name + "size: " + _objectList.size());
		for(ArrayList<DomainObject> list : _objectList) {
			System.out.println("size: " + list.size());
			for(DomainObject obj : list) {
				System.out.println(obj.getName() + " " + obj.getDescription());
			}
		}
	}

	@Override
	public String getStringContent(DomainConverter converter, int index) {
		if(_objectList.get(_assignmentIndex).size() == 0) {
			return "<" + _name + "/>";
		}
		String string = "<" + _name + "><objects>";
		for(DomainObject obj : _objectList.get(index)) {
			string += obj.toXMLString();
		}
		string += "</objects></" + _name + ">";
		return string;
	}

	@Override
	public void removeAssignment(int index) {
		_objectList.remove(index);
	}

	@Override
	public void setDomainModel(DomainModel model) {
		
	}

	@Override
	public void setState(AssignmentState state) {
		
	}

	@Override
	public void setActionObservers(Collection<IlmModule> values) {
		
	}

}

package ilm.framework.assignment.model;

import java.util.ArrayList;
import java.util.Observable;

public final class AssignmentState extends Observable {

	private ArrayList<DomainObject> _objectList;

    public AssignmentState() {
        _objectList = new ArrayList<DomainObject>();
    }
        
    public final void add(DomainObject object) {
        _objectList.add(object);
        setChanged();
        notifyObservers();
    }
    
    public final boolean remove(DomainObject object) {
        boolean isRemoved = _objectList.remove(object);
        setChanged();
        notifyObservers();
        return isRemoved;
    }
    
    public final DomainObject remove(int index) {
        DomainObject removedObject = _objectList.remove(index);
        setChanged();
        notifyObservers();
        return removedObject;
    }
    
    public final DomainObject get(int index) {
        return _objectList.get(index);
    }
    
    public final DomainObject getFromName(String name) {
    	for(DomainObject obj : _objectList) {
    		if(obj.getName().equals(name)) {
    			return obj;
    		}
    	}
    	return null;
    }
    
    public final DomainObject getFromDescription(String description) {
    	for(DomainObject obj : _objectList) {
    		if(obj.getDescription().equals(description)) {
    			return obj;
    		}
    	}
    	return null;
    }
    
    public final ArrayList<DomainObject> getList() {
    	return _objectList;
    }
    
    public final void setList(ArrayList<DomainObject> list) {
    	_objectList = list;
    }
    
}

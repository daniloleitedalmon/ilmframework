package ilm.framework.assignment.model;

import java.util.ArrayList;
import java.util.Observable;

public class AssignmentState extends Observable {

	private ArrayList<DomainObject> _objectList;

    public AssignmentState() {
        _objectList = new ArrayList<DomainObject>();
    }
    
    public final void add(DomainObject newObject) {
        _objectList.add(newObject);
        newObject.setAddedState();
        setChanged();
        notifyObservers(newObject);
        newObject.setPropertyChangeState();
    }
    
    public final boolean remove(DomainObject objectToBeRemoved) {
        objectToBeRemoved.setRemovedState();
        boolean isRemoved = _objectList.remove(objectToBeRemoved);
        setChanged();
        notifyObservers(objectToBeRemoved);
        return isRemoved;
    }
    
    public final DomainObject remove(int index) {
        _objectList.get(index).setRemovedState();
        DomainObject removedObject = _objectList.remove(index);
        setChanged();
        notifyObservers(removedObject);
        return removedObject;
    }
    
    public final DomainObject get(int index) {
        return _objectList.get(index);
    }
    
    public final int size() {
        return _objectList.size();
    }
    
}

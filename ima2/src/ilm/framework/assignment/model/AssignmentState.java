package ilm.framework.assignment.model;

import java.util.ArrayList;
import java.util.Observable;

public class AssignmentState extends Observable {

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
    
    public final int size() {
        return _objectList.size();
    }
    
}

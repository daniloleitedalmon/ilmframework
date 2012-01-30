package ilm.framework.assignment.model;

import java.util.ArrayList;
import java.util.Observable;

public abstract class DomainAction extends Observable implements Cloneable {

    private String _name;
    protected String _description;
    protected ArrayList<DomainObject> _parameterList;
    protected AssignmentState _currentState;
    private boolean _isUndo;
    private boolean _isRedo;

    public DomainAction(String name, String description) {
    	_name = name;
    	_description = description;
    	_parameterList = new ArrayList<DomainObject>();
    }
    
    public final void execute() {
        executeAction();
        _isUndo = false;
        setChanged();
        notifyObservers();
        _isRedo = false;
    }
    
    protected abstract void executeAction();
    
    
    public final void undo() {
        undoAction();
        _isUndo = true;
        setChanged();
        notifyObservers();
    }
    
    protected abstract void undoAction();
    
    
    public final boolean isUndo() {
        return _isUndo;
    }
    
    public final void setRedo(boolean b) {
        _isRedo = b;
    }
    
    public final boolean isRedo() {
        return _isRedo;
    }
    
    
    public final void setObjectParameter(ArrayList<DomainObject> parameterList) {
        _parameterList = parameterList;
    }
    
    public final void setState(AssignmentState currentState) {
        _currentState = currentState;
    }
    
    public final AssignmentState getState() {
    	return _currentState;
    }
    
    
    public final String getName() {
        return _name;
    }
    
    public final String getDescription() {
        return _description;
    }
    
    
    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    public abstract boolean equals(DomainAction a);

}

package ilm.framework.assignment.model;

import java.util.ArrayList;
import java.util.Observable;

public abstract class DomainAction extends Observable implements Cloneable {

    private String _name;
    private String _description;
    protected ArrayList<DomainObject> _parameterList;
    protected AssignmentState _currentState;
    private boolean _isUndo;
    private boolean _isRedo;

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

}

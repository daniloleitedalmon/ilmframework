package ilm.framework.assignment.model;

import ilm.framework.domain.DomainModel;

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
    
    /**
     * Defines the DomainModel on which this action is
     * executed. The concrete DomainAction classes must
     * have an attribute that is from a class that
     * inherits from DomainModel, and this method set
     * its value. It is called internally from the framework.
     * @param an instance of a class that inherits from DomainModel
     * 
     * @see example.ilm.model.ActionAddSubString
     */
    public abstract void setDomainModel(DomainModel model);
    
    /**
     * Template Method that is called when this action is being
     * executed. It encapsulates Hook Method called "executeAction"
     */
    public final void execute() {
        executeAction();
        _isUndo = false;
        setChanged();
        notifyObservers();
        _isRedo = false;
    }
    
    /**
     * Hook Method that must be implemented to call methods
     * from DomainModel when this action is being executed
     * 
     * @see example.ilm.model.ActionAddSubString
     */
    protected abstract void executeAction();
    
    /**
     * Template Method that is called when this action is being
     * undone. It encapsulates Hook Method called "undoAction"
     */
    public final void undo() {
        undoAction();
        _isUndo = true;
        setChanged();
        notifyObservers();
    }
    
    /**
     * Hook Method that must be implemented to call methods
     * from DomainModel when this action is being undone
     * 
     * @see example.ilm.model.ActionAddSubString
     */
    protected abstract void undoAction();
    
    /**
     * @return a boolean that indicates whether this action is a "undoing" action
     */
    public final boolean isUndo() {
        return _isUndo;
    }
    
    /**
     * Defines whether this action is a "redoing" action
     * @param the new state of this action regaring its "redoing" attribute
     */
    public final void setRedo(boolean b) {
        _isRedo = b;
    }
    
    /**
     * @return a boolean that indicates whether this action is a "redoing" action
     */
    public final boolean isRedo() {
        return _isRedo;
    }
    
    /**
     * Defines the list of DomainObject which are the parameters
     * that will be used for the execution of this action
     * @param a list of DomainObject
     */
    public final void setObjectParameter(ArrayList<DomainObject> parameterList) {
        _parameterList = parameterList;
    }
    
	/**
	 * Defines a new assignment state for this action
     * @param the new action's assignment state
	 */
    public final void setState(AssignmentState currentState) {
        _currentState = currentState;
    }
    
	/**
	 * @return the action's current assignment state
	 */
    public final AssignmentState getState() {
    	return _currentState;
    }
    
	/**
	 * @return the action's name
	 */
    public final String getName() {
        return _name;
    }

	/**
	 * @return the action's description
	 */
    public final String getDescription() {
        return _description;
    }
    
    /**
     * Defines a new description for this action
     * @param the new action's description 
     */
	public final void setDescription(String string) {
		_description = string;
	}
    
    /**
     * @return a copy of the current DomainAction
     * 
     * Usually used in AssignmentModules for storing lists of
     * actions
     */
    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
	/**
	 * Compares this with another DomainAction
	 * @param the other action to be compare to
	 * @return the result of the comparison, true or false
	 * 
	 * @see example.ilm.model.ActionAddSubString for a simple example
	 */
    public abstract boolean equals(DomainAction a);
    
}

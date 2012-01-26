package ilm.framework.assignment.modules;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;

public class UndoRedoModule extends AssignmentModule {

	private ArrayList<Stack<DomainAction>> _undoStack;
	private ArrayList<Stack<DomainAction>> _redoStack;
	
	public UndoRedoModule() {
		_undoStack = new ArrayList<Stack<DomainAction>>();
		_redoStack = new ArrayList<Stack<DomainAction>>();
		
		_name = "undo_redo";
		_gui = new UndoRedoModuleToolbar();
		_observerType = ACTION_OBSERVER;
	}
	
	public void undo() {
		DomainAction action = _undoStack.get(_assignmentIndex).pop();
		_redoStack.get(_assignmentIndex).push(action);
		action.undo();
	}
	
	public void redo() {
		DomainAction action = _redoStack.get(_assignmentIndex).pop();
		action.setRedo(true);
		//_undoStack.push(action); -> it is already done within action.execute()
		action.execute();
	}
	
	public boolean isUndoStackEmpty() {
		return _undoStack.get(_assignmentIndex).isEmpty();
	}
	
	public boolean isRedoStackEmpty() {
		return _redoStack.get(_assignmentIndex).isEmpty();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof DomainAction) {
			DomainAction action = (DomainAction)o;
			
			if(action.isUndo()) {
				setChanged();
				notifyObservers();
			}
			else {
				if(!action.isRedo()) {
					_redoStack.get(_assignmentIndex).clear();
				}
				_undoStack.get(_assignmentIndex).push((DomainAction)action.clone());
				setChanged();
				notifyObservers();
			}
		}
	}

	@Override
	public void setContentFromString(DomainConverter converter,	String moduleContent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAssignment() {
		_undoStack.add(new Stack<DomainAction>());
		_redoStack.add(new Stack<DomainAction>());
	}

}

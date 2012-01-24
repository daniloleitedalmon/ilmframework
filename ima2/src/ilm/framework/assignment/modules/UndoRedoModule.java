package ilm.framework.assignment.modules;

import java.util.Observable;
import java.util.Stack;

import ilm.framework.assignment.model.DomainAction;

public class UndoRedoModule extends AssignmentModule {

	private Stack<DomainAction> _undoStack;
	private Stack<DomainAction> _redoStack;
	
	public UndoRedoModule() {
		_undoStack = new Stack<DomainAction>();
		_redoStack = new Stack<DomainAction>();
		
		_name = "undo_redo";
		_gui = new UndoRedoModuleToolbar();
		_observerType = ACTION_OBSERVER;
	}
	
	public void undo() {
		DomainAction action = _undoStack.pop();
		_redoStack.push(action);
		action.undo();
	}
	
	public void redo() {
		DomainAction action = _redoStack.pop();
		action.setRedo(true);
		//_undoStack.push(action); -> it is already done within action.execute()
		action.execute();
	}
	
	public boolean isUndoStackEmpty() {
		return _undoStack.isEmpty();
	}
	
	public boolean isRedoStackEmpty() {
		return _redoStack.isEmpty();
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
					_redoStack.clear();
				}
				_undoStack.push((DomainAction)action.clone());
				setChanged();
				notifyObservers();
			}
		}
	}

}

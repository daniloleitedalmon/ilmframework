package ilm.framework.modules.assignment;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;

public class UndoRedoModule extends AssignmentModule {

	private ArrayList<Stack<DomainAction>> _undoStack;
	private ArrayList<Stack<DomainAction>> _redoStack;
	
	public UndoRedoModule() {
		_undoStack = new ArrayList<Stack<DomainAction>>();
		_redoStack = new ArrayList<Stack<DomainAction>>();
		
		_name = IlmProtocol.UNDO_REDO_MODULE_NAME;
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
		ArrayList<DomainAction> actionList = converter.convertStringToAction(moduleContent);
		Stack<DomainAction> actionStack = new Stack<DomainAction>();
		for(DomainAction action : actionList) {
			actionStack.push(action);
		}
		_undoStack.add(actionStack);
	}

	@Override
	public void addAssignment() {
		_undoStack.add(new Stack<DomainAction>());
		_redoStack.add(new Stack<DomainAction>());
	}

	@Override
	public void print() {
		System.out.println("Name: " + _name + " size: " + _undoStack.size());
		for(Stack<DomainAction> stack : _undoStack) {
			System.out.println("size: " + stack.size());
			for(DomainAction a : stack) {
				System.out.println(a.getName() + " " + a.getDescription());
			}
		}
	}

	@Override
	public String getStringContent(DomainConverter converter) {
		if(_undoStack.get(_assignmentIndex).size() == 0 & _redoStack.get(_assignmentIndex).size() == 0) {
			return "<" + _name + "/>";
		}
		String string = "<" + _name + ">"; 
		if(_undoStack.get(_assignmentIndex).size() > 0) {
			string += "<undostack>";
			for(DomainAction action : _undoStack.get(_assignmentIndex)) {
				string += action.toXMLString();
			}
			string += "</undostack>";
		}
		if(_redoStack.get(_assignmentIndex).size() > 0) {
			string += "<redostack>";
			for(DomainAction action : _redoStack.get(_assignmentIndex)) {
				string += action.toXMLString();
			}
			string += "</redostack>";
		}
		string += "</" + _name + ">";
		return string;
	}

	@Override
	public void removeAssignment(int index) {
		_undoStack.remove(index);
		_redoStack.remove(index);
	}

}

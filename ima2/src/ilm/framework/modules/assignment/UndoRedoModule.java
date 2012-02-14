package ilm.framework.modules.assignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Stack;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

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
	public void setContentFromString(DomainConverter converter,	int index, String moduleContent) {
		if(_undoStack.size() == index && _redoStack.size() == index) {
			addAssignment();
		}
		String undoCommands = getActions("undostack", moduleContent);
		String redoCommands = getActions("redostack", moduleContent);
		for(DomainAction action : converter.convertStringToAction(undoCommands)) {
			_undoStack.get(index).push(action);
		}
		for(DomainAction action : converter.convertStringToAction(redoCommands)) {
			_redoStack.get(index).push(action);
		}
	}

	private String getActions(String string, String moduleContent) {
		int startIndex = moduleContent.indexOf("<" + string + ">") + 2 + string.length();
		int endIndex = moduleContent.indexOf("</" + string + ">");
		if(startIndex == -1 || endIndex == -1) {
			return "";
		}
		String s = moduleContent.substring(startIndex, endIndex);
		return s;
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
	public String getStringContent(DomainConverter converter, int index) {
		if(_undoStack.get(index).size() == 0 & _redoStack.get(index).size() == 0) {
			return "<" + _name + "/>";
		}
		String string = "<" + _name + ">"; 
		if(_undoStack.get(index).size() > 0) {
			string += "<undostack>";
			for(DomainAction action : _undoStack.get(index)) {
				string += action.toString();
			}
			string += "</undostack>";
		}
		if(_redoStack.get(index).size() > 0) {
			string += "<redostack>";
			for(DomainAction action : _redoStack.get(index)) {
				string += action.toString();
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

	@Override
	public void setDomainModel(DomainModel model) {
		for(Stack<DomainAction> stack : _undoStack) {
			for(DomainAction action : stack) {
				action.setDomainModel(model);
			}
		}
		for(Stack<DomainAction> stack : _redoStack) {
			for(DomainAction action : stack) {
				action.setDomainModel(model);
			}
		}
	}

	@Override
	public void setState(AssignmentState state) {
		for(DomainAction action : _undoStack.get(_undoStack.size()-1)) {
			action.setState(state);
		}
		for(DomainAction action : _redoStack.get(_redoStack.size()-1)) {
			action.setState(state);
		}
	}

	@Override
	public void setActionObservers(Collection<IlmModule> values) {
		for(IlmModule m : values) {
			if(m instanceof AssignmentModule) {
				for(Stack<DomainAction> stack : _undoStack) {
					for(DomainAction action : stack) {
						action.addObserver((AssignmentModule)m);
					}
				}
				for(Stack<DomainAction> stack : _redoStack) {
					for(DomainAction action : stack) {
						action.addObserver((AssignmentModule)m);
					}
				}
			}
		}
	}

}

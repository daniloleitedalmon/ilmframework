package ilm.framework.modules;

import java.util.Observable;

public abstract class IlmModule extends Observable {

	protected String _name;
	protected int _assignmentIndex;
	protected IlmModuleToolbar _gui;
	
	public String getName() {
		return _name;
	}
	
	public IlmModuleToolbar getGUI() {
		addObserver(_gui);
		return _gui;
	}
	
	public void setAssignmentIndex(int index) {
		_assignmentIndex = index;
		setChanged();
		notifyObservers();
	}
	
	public abstract void print();
	
}

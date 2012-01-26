package ilm.framework.assignment.modules;

import ilm.framework.domain.DomainConverter;

import java.util.Observable;
import java.util.Observer;

public abstract class AssignmentModule extends Observable implements Observer, Cloneable {

	protected String _name;
	protected AssignmentModuleToolbar _gui;
	
	protected int _observerType;
	public static final int ACTION_OBSERVER = 1;
	public static final int OBJECT_OBSERVER = 2;
	public static final int ACTION_OBJECT_OBSERVER = 3;
	
    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

	public String getName() {
		return _name;
	}

	public AssignmentModuleToolbar getGUI() {
		addObserver(_gui);
		return _gui;
	}
	
	public int getObserverType() {
		return _observerType;
	}
	
	public abstract void setContentFromString(DomainConverter converter, String moduleContent);
	
}

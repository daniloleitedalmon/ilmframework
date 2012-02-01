package ilm.framework.modules;

import ilm.framework.domain.DomainConverter;

import java.util.Observer;

public abstract class AssignmentModule extends IlmModule implements Observer, Cloneable {
	
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
	
	public int getObserverType() {
		return _observerType;
	}
	
	public abstract void addAssignment();
	
	public abstract void setContentFromString(DomainConverter converter, String moduleContent);
	
	public abstract String getStringContent(DomainConverter converter);
	
}

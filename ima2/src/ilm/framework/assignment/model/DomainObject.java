package ilm.framework.assignment.model;

public abstract class DomainObject {

	private String _name;
	private String _description;
	private int _changeState; //0 - property changed, 1 - added, -1 - removed

	public String getName() {
		return _name;
	}
	
	public String getDescription() {
		return _description;
	}
    
    public int getChangeState() {
        return _changeState;
    }

	public void setRemovedState() {
		_changeState = -1;
	}

	public void setPropertyChangeState() {
		_changeState = 0;
	}

	public void setAddedState() {
		_changeState = 1;
	}
    
}

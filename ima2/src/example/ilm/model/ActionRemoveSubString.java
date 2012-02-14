package example.ilm.model;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ActionRemoveSubString extends DomainAction {

	/**
	 * @attribute The action's data, a substring
	 * @attribute The system's domain model
	 */
    private String _substring;
    private IlmDomainModel _domain;
    
    public ActionRemoveSubString(String name, String description) {
        super(name, description);
    }
    
    public ActionRemoveSubString(String name, String description, String substring) {
        super(name, description);
        setSubString(substring);
    }
    
    /**
     * Defines the DomainModel on which this action 
     * can happen. It must be converted to IlmDomainModel
     * so this class can call its specific methods.
     * @param an IlmDomainModel
     */
	@Override
	public void setDomainModel(DomainModel model) {
		_domain = (IlmDomainModel)model;
	}
    
    /**
     * Defines the substring that this action will add
     * @param the substring of this action
     */
    public void setSubString(String substring) {
        _substring = substring;
        _description = "del: " + substring;
    }
    
    /**
     * Method that is called when this action is executed.
     * It adds a substring to the current state by calling
     * a method of IlmDomainModel.
     */
    @Override
    protected void executeAction() {
        _domain.RemoveSubString(_currentState);
    }

    /**
     * Method that is called when this action is undone.
     * So, the inverse of adding a substring is removing one.
     */
    @Override
    protected void undoAction() {
        _domain.AddSubString(_currentState, _substring);
    }

    /**
     * Compares this to another DomainAction
     * In this case, the description is the action's identifier
     * so it is sufficient to compare only the description.
     */
	@Override
	public boolean equals(DomainAction a) {
		if(getName().equals(a.getName()) &
			getDescription().equals(a.getDescription())) {
			return true;
			}
		return false;
	}

	/**
	 * This method is used for converting this object into string
	 * 
	 * @see example.ilm.model.IlmDomainConverter
	 */
	@Override
	public String toString() {
		return "<removeaction><name>" + getName() + "</name><description>" + getDescription() + 
				"</description><substring>" + _substring + "</substring></removeaction>";
	}
	
}

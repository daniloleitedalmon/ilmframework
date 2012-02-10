package example.ilm.model;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ActionAddSubString extends DomainAction {
    
    private String _substring; 
    private IlmDomainModel _domain;
    
    public ActionAddSubString(String name, String description) {
        super(name, description);
    }
    
    public ActionAddSubString(String name, String description, String substring) {
    	super(name, description);
    	setSubString(substring);
    }
       
    public void setSubString(String substring) {
        _substring = substring;
        _description = "add: " + substring;
    }

    public String getSubString() {
    	return _substring;
    }
    

    protected void executeAction() {
        _domain.AddSubString(_currentState, _substring);
    }

    protected void undoAction() {
        _domain.RemoveSubString(_currentState);
    }

	@Override
	public boolean equals(DomainAction a) {
		if(getName().equals(a.getName()) &
			getDescription().equals(a.getDescription())) {
			return true;
		}
		return false;
	}

	@Override
	public String toXMLString() {
		return "<addaction><name>" + getName() + "</name><description>" + getDescription() + 
				"</description><substring>" + _substring + "</substring></addaction>";
	}

	@Override
	public void setDomainModel(DomainModel model) {
		_domain = (IlmDomainModel)model;
	}

}

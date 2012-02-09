package example.ilm.model;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ActionRemoveSubString extends DomainAction {
    
    private String _substring;
    private IlmDomainModel _domain;
    
    public ActionRemoveSubString(String name, String description) {
        super(name, description);
    }
    
    public ActionRemoveSubString(String name, String description, String substring) {
        super(name, description);
        setSubString(substring);
    }
    
    public void setDomain(IlmDomainModel domain) {
    	_domain = domain;
    }
    
    public void setSubString(String substring) {
        _substring = substring;
        _description = "del: " + substring;
    }
    

    public String getSubString() {
    	return _substring;
    }
    
    protected void executeAction() {
        _domain.RemoveSubString(_currentState);
    }

    protected void undoAction() {
        _domain.AddSubString(_currentState, _substring);
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
		return "<removeaction><name>" + getName() + "</name><description>" + getDescription() + 
				"</description><substring>" + _substring + "</substring></removeaction>";
	}

	@Override
	public void setDomainModel(DomainModel model) {
		_domain = (IlmDomainModel)model;
	}

}

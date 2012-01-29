package ilm.model;

import ilm.framework.assignment.model.DomainAction;

public class ActionAddSubString extends DomainAction {
    
    private String _substring; 
    private IlmDomainModel _domain;
    
    public ActionAddSubString(String name, 
                              String description) {
        super(name, description);

    }
    
    public void setDomain(IlmDomainModel domain) {
        _domain = domain;
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

}

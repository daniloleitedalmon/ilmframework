package ilm.model;

import ilm.framework.assignment.model.DomainAction;

public class ActionRemoveSubString extends DomainAction {
    
    private String _substring;
    private IlmDomainModel _domain;
    
    public ActionRemoveSubString(String name, 
                                 String description,
                                 IlmDomainModel domain) {
        super(name, description);
        _domain = domain;
    }
    
    public void setSubString(String substring) {
        _substring = substring;
    }
    

    protected void executeAction() {
        _domain.RemoveSubString(_currentState);
    }

    protected void undoAction() {
        _domain.AddSubString(_currentState, _substring);
    }

}

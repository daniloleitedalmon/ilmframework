package ilm.model;

import ilm.framework.assignment.model.DomainObject;

public class ObjectSubString extends DomainObject {
    
    private String _substring;
    
    public ObjectSubString(String name, String description, String substring) {
        super(name, description);
        _substring = substring;
    }
    
    public String getSubString() {
        return _substring;
    }
    
}

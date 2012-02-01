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

	@Override
	public boolean equals(DomainObject o) {
		ObjectSubString obj = (ObjectSubString)o;
		if(getSubString().equals(obj.getSubString()) & 
		   getName().equals(obj.getName()) &
		   getDescription().equals(obj.getDescription())) {
			return true;
		}
		return false;
	}

	@Override
	public String toXMLString() {
		return "<objectsubstring><name>" + getName() + "</name><description>" + getDescription() + 
				"</description><substring>" + _substring + "</substring></objectsubstring>";
	}
    
}

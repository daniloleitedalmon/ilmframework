package example.ilm.model;

import ilm.framework.assignment.model.DomainObject;

public class ObjectSubString extends DomainObject {
    
	/**
	 * The data of the object, a "substring"
	 */
    private String _substring;
    
    public ObjectSubString(String name, String description, String substring) {
        super(name, description);
        _substring = substring;
    }
    
    /**
     * @return the object's substring
     */
    public String getSubString() {
        return _substring;
    }

    /**
     * Comparison between two SubStringObjects
     * It compares only the substrings, which is the real data
     */
	@Override
	public boolean equals(DomainObject o) {
		ObjectSubString obj = (ObjectSubString)o;
		if(getSubString().equals(obj.getSubString())) {
			return true;
		}
		return false;
	}

	/**
	 * Convert this object to a XML String, placing the fields
	 * between the fields' names.
	 */
	@Override
	public String toString() {
		return "<objectsubstring><name>" + getName() + "</name><description>" + getDescription() + 
				"</description><substring>" + _substring + "</substring></objectsubstring>";
	}
    
}

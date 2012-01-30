package ilm.framework.assignment.model;

public abstract class DomainObject {

	private String _name;
	private String _description;

	public DomainObject(String name, String description) {
		_name = name;
		_description = description;
	}
	
	public String getName() {
		return _name;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public abstract boolean equals(DomainObject o);
    
}

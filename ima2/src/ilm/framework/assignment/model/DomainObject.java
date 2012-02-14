package ilm.framework.assignment.model;

public abstract class DomainObject {

	private String _name;
	private String _description;

	public DomainObject(String name, String description) {
		_name = name;
		_description = description;
	}
	
	/**
	 * @return the object's name
	 */
	public final String getName() {
		return _name;
	}
	
	/**
	 * @return the object's description
	 */
	public final String getDescription() {
		return _description;
	}
	
	/**
	 * Compares this with another DomainObject
	 * @param the other object to be compare to
	 * @return the result of the comparison, true or false
	 * 
	 * @see example.ilm.model.ObjectSubString for a simple example
	 */
	public abstract boolean equals(DomainObject o);
	
}

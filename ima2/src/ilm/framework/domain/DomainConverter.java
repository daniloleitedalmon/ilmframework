package ilm.framework.domain;

import java.util.ArrayList;

import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;

public interface DomainConverter {
    
	/**
	 * Converts a string to a list of DomainObject
	 * @param objectListDescription
	 * @return a list of DomainObjects created using the description. Return an empty
	 * list if the string is empty or incompatible.
	 * 
	 * @see example.ilm.model.IlmDomainConverter for a simple example
	 * 
     * @throws it is not yet implemented but this method may in the future
     * throw exceptions due to parsing errors
	 */
    public ArrayList<DomainObject> convertStringToObject(String objectListDescription);
    
    /**
     * Converts a list of DomainObject to a string
     * @param objectList
     * @return a string containing the description of the list of DomainObjects.
     * 
	 * @see example.ilm.model.IlmDomainConverter for a simple example 
     */
    public String convertObjectToString(ArrayList<DomainObject> objectList);
    
    /**
     * Converts a string to a list of DomainAction
     * @param actionListDescription
     * @return a list of DomainActions created using the description. Return an empty
	 * list if the string is empty or incompatible.
	 * 
	 * @see example.ilm.model.IlmDomainConverter for a simple example
	 * 
     * @throws it is not yet implemented but this method may in the future
     * throw exceptions due to parsing errors
     */
    public ArrayList<DomainAction> convertStringToAction(String actionListDescription);
    
    /**
     * Converts a list of DomainAction to a string
     * @param actionList
     * @return a string containing the description of the list of DomainActions
     * 
     * @see example.ilm.model.IlmDomainConverter for a simple example 
     */
    public String convertActionToString(ArrayList<DomainAction> actionList);

}